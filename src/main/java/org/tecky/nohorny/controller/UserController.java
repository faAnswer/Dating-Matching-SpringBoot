package org.tecky.nohorny.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.tecky.nohorny.dto.JSONResponse;
import org.tecky.nohorny.entities.UserEntity;
import org.tecky.nohorny.entities.UserInfoEntity;
import org.tecky.nohorny.services.intf.ILikeService;
import org.tecky.nohorny.services.intf.IRegService;
import org.tecky.nohorny.services.intf.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    IRegService iRegService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ILikeService iLikeService;

    @Autowired
    IUserService iUserService;

    @GetMapping(value = "/like")
    public ResponseEntity<?> like(@RequestParam Map<String, String> userInfo, Authentication authentication) throws Exception {

        String toUserName = userInfo.get("username");

        if(iLikeService.changeLikeStatus(authentication,toUserName)){

            return JSONResponse.ok("like");

        } else {

            return JSONResponse.ok("nolike");
        }
    }

    @Value("${minio.picbucketName}")
    private String bucketName;

    @Value("${core.storageservice.upload.api}")
    private String storageServiceAPI;


    private Pattern pattern = Pattern.compile("\\.[a-zA-Z]+$");

    @PostMapping(value = "profile")
    public ResponseEntity<?> profile(@RequestParam(value = "gender", required = false) String gender,
                                                        @RequestParam(value = "sexual", required = false) String sexual,
                                                        @RequestParam(value = "hobbies", required = false) String hobbies,
                                                        @RequestParam(value = "age", required = false) Integer age,
                                                        @RequestParam(value = "districtid", required = false) Integer districtid,
                                                        @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                                        Authentication authentication) throws Exception {

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        Matcher matcher = this.pattern.matcher(avatar.getOriginalFilename());

        String fileExtension = "";

        while (matcher.find()) {

            fileExtension += matcher.group(0);
        }

        File tempFile = null;
        tempFile = File.createTempFile("temp", fileExtension);
        avatar.transferTo(tempFile);

        map.add("file", new FileSystemResource(tempFile));
        map.add("bucketName", this.bucketName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();

        String jsonResponse;

        jsonResponse = restTemplate.postForEntity(this.storageServiceAPI, httpEntity, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> mapResponse = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        String picId = mapResponse.get("filename");

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        userInfoEntity.setHobbies(hobbies);
        userInfoEntity.setAge(age);
        userInfoEntity.setGender(gender);
        userInfoEntity.setSexual(sexual);
        userInfoEntity.setDistrictid(districtid);
        userInfoEntity.setPicId(picId);

        iUserService.upDateProfile(userInfoEntity, authentication);

        return JSONResponse.ok("sucessful");
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userInfo, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{

        if(userInfo.get("username") == null){

            return JSONResponse.fail("Username must not be null", HttpStatus.BAD_REQUEST);
        }

        if(userInfo.get("password") == null){

            return JSONResponse.fail("Password must not be null", HttpStatus.BAD_REQUEST);
        }

        if(userInfo.get("email") == null){

            return JSONResponse.fail("Email must not be null", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userInfo.get("username"));
        userEntity.setShapassword(userInfo.get("password"));
        userEntity.setEmail(userInfo.get("email"));

        iRegService.regNewUser(userEntity);

        //return login(userInfo, session, request, response);

        authenticate(userInfo.get("username"), userInfo.get("password"), request);
        ResponseEntity<?> responseEntity = JSONResponse.ok(userInfo.get("username"));

        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userInfo, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{

        log.info("authLogin");

        authenticate(userInfo.get("username"), userInfo.get("password"), request);


        ResponseEntity<?> responseEntity = JSONResponse.ok(userInfo.get("username"));

        return responseEntity;
    }

    private void authenticate(String username, String password, HttpServletRequest request) throws Exception {
        try {

            //為了方便我們訪問 SecurityContext 對像, Spring Security 提供了 SecurityContextHolder 類,
            //通過 SecurityContextHolder.getContext() 即可獲取 SecurityContext 對像.
            //SecurityContextHolder 采用 ThreadLocal 方式存儲 SecurityContext 對像,
            //這樣就能保證我們調用 SecurityContextHolder.getContext() 得到的永遠是當前用戶的 SecurityContext.

            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

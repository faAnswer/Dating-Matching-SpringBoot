let API_ORIGIN = 'http://47.92.137.0:9999'
// let API_ORIGIN = 'http://dating.com'

function checkAPI(res) {
    if (res.status == 403) {
        let params = new URLSearchParams()
        params.set('redirect_uri', location.href)
        // need to login first
        console.log("should login");
        Swal.fire({
            icon: "error",
            title: "Need to login",
            showConfirmButton: false,
            footer: `<a href="/login.html?${params}">Go to login page</a>`,
        });
    }
}

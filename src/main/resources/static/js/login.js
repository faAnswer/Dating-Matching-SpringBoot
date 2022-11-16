let params = new URLSearchParams(location.search)

//register function
async function signup(event) {
    event.preventDefault()
    let form = event.target

    try {
        let url = form.action.replace(location.origin, API_ORIGIN)

        console.log(url);

        let res = await fetch(url, {
            method: form.method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: registerUsername.value,
                email: registerEmail.value,
                password: registerPassword.value,
                RepeatPassword: registerRepeatPassword.value,
            })
        })
        console.log('post signup response:', res.status, res.statusText)
        message.textContent = res.statusText

        let json = await res.json() //res.json = put value in json file

        if (json.error) {
            message.textContent = json.error
        } else {

            location.href = "/index.html"
        }
    } catch (error) {
        console.error('failed to post signup:', error)
        message.textContent = "Network Failure"
    }
}
//sign-in function
async function signIn(event) {
    event.preventDefault()
    let form = event.target


    try {
        let url = form.action.replace(location.origin, API_ORIGIN)
        let res = await fetch(url, {
            method: form.method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: loginNam.value,
                password: loginPassword.value,
            })

        })
        console.log('post signup response:', res.status, res.statusText)
        message.textContent = res.statusText

        let json = await res.json()

        if (json.error) {
            message.textContent = json.error
        } else {
            location.href = nextPageHref
        }
    } catch (error) {
        console.error('failed to post signup:', error)
        message.textContent = "Network Failure"
    }
}
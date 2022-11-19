console.log('loading chat plugin...')


fetch('/chat-plugin.html')
    .then(res => res.text())
    .then(html => {
        // console.log(html)
        let dom = new DOMParser()
        let doc = dom.parseFromString(html, 'text/html')
        for (let element of doc.querySelectorAll('body > *')) {
            if (element.tagName == 'SCRIPT' && element.className != 'custom-script') {
                continue
            }
            console.log(element.tagName, element.className)
            document.body.appendChild(element)
        }
    })
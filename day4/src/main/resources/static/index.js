$(document).ready(() => {

    // wait for username input field to unfocus
    $("#username-input").focusout(() => {

        const username = $("#username-input")[0].value

        fetch("cart/" + username)
            .then((response) => {
                return response.text();
            }).then((html) => {
                console.log(html)
                $("#cart-items-list")[0].innerHTML = html;
            }).catch((err) => {
                console.warn("Something went wrong", err);
            })

    })

    $("#form").submit((e) => {
        const addItemUrl = "/cart"
        const form = e.target;

        fetch(addItemUrl, {
            method: form.method,
            body: new FormData(form)
        }).then((response) => {
            return response.text();
        }).then((html) => {
            console.log(html)
            $("#cart-items-list")[0].innerHTML = html;
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

        e.preventDefault();
    })
})
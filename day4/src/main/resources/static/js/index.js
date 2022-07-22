// FIXME: does not fetch if not on root
$(document).ready(() => {

    // wait for username input field to unfocus
    $("#username-input").focusout(() => {

        const username = $("#username-input")[0].value

        // async GET request to fetch user's cart
        fetch("/cart/" + username)
            .then((response) => {
                return response.text();
            }).then((html) => {
                $("#cart-items-list")[0].innerHTML = html;
            }).catch((err) => {
                console.warn("Something went wrong", err);
            })

    });

});
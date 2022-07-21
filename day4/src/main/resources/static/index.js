$(document).ready(() => {

    // wait for username input field to unfocus
    $("#username-input").focusout(() => {

        const username = $("#username-input")[0].value
        // const username = (usernameInput != "") ? usernameInput : "empty" 


        // async GET request to fetch user's cart
        fetch("cart/" + username, {method: "post"})
            .then((response) => {
                return response.text();
            }).then((html) => {
                console.log(html)
                $("#cart-items-list")[0].innerHTML = html;
            }).catch((err) => {
                console.warn("Something went wrong", err);
            })

    })

    // on adding new item
    // $("#form").submit((e) => {
    //     const addItemUrl = "/add-item"
    //     const form = e.target;

    //     // async POST request to add new item to user's cart
    //     fetch(addItemUrl, {
    //         method: form.method,
    //         body: new FormData(form)
    //     }).then((response) => {
    //         return response.text();
    //     }).then((html) => {
    //         console.log(html)
    //         $("#cart-items-list")[0].innerHTML = html;
    //     }).catch((err) => {
    //         console.warn("Something went wrong", err);
    //     })

    //     e.preventDefault();
    // })

    // // on deleting item
    // $("#delete-btn").click((e) => {
    //     const deleteItemUrl = "/add-item"
    //     const form = e.target;

    //     // async POST request to delete item from user's cart
    //     fetch(deleteItemUrl, {
    //         method: form.method,
    //         body: new FormData(form)
    //     }).then((response) => {
    //         return response.text();
    //     }).then((html) => {
    //         console.log(html)
    //         $("#cart-items-list")[0].innerHTML = html;
    //     }).catch((err) => {
    //         console.warn("Something went wrong", err);
    //     })

})

// })
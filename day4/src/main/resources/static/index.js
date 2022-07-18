$(document).ready(function () {

    // wait for username input field to mouse in then mouse out
    $("#username-input").focusout(function () {

        var username = document.getElementById("username-input").value

        fetch("cart/" + username)
            .then(function (response) {
                return response.text();
            }).then(function (html) {
                console.log(html)
                document.getElementById("placeholder").innerHTML += html;
            }).catch(function (err) {
                console.warn("Something went wrong", err);
            })

    })
})



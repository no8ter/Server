<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body {
        margin-top: 0;
        margin-left: 10%;
        margin-right: 10%;
        min-height: 100vh;
        display: flex;
        align-items: center;
        flex-direction: column;
    }
    input{
        width:95%;
    }
    input[type=submit]{
        width:100%;
    }
    button{
        width: 100%;
    }
    form {
        margin-bottom: 0;
    }
    div.form{
        display: flex;
        flex-direction: column;
    }
    div.send_message_form{
        display: flex;
        flex-direction: row;
    }
    div.main{
        margin-left: 10%;
        margin-right: 10%;
        min-height: 100vh;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
    }
    div.messenger{
        height: 80%;
        width: 60%;
        margin-top: 3%;
        display: flex;
        flex-direction: column;
    }
    div.menu{
        display: flex;
        flex-direction: row;
        align-items: center;
    }
    div.menu > a > button{
        margin: 10px;
    }
    form#send_message{
        width: 100%;
        flex-direction: row;
        display: flex;
    }
    form#send_message > input[type=text]{
        width: 80%;
    }
    form#send_message > input[type=submit]{
        width: 20%;
    }
</style>
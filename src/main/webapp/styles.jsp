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
        width: 95%;
        margin: 5px;
    }
    input[type=submit]{
        width: 100%;
    }
    input[type=radio]{
        width: auto;
    }
    input[type=date]{
        width: 95%;
    }
    button{
        width: 100%;
    }
    form {
        margin-bottom: 0;
    }
    div.wrapper{
        border: 1px solid #888;
        display: inline-block;
        padding:20px;
    }
    div.form{
        display: flex;
        flex-direction: column;
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
    div.menu{
        display: flex;
        flex-direction: row;
        align-items: center;
    }
    div.menu > a > button{
        margin: 10px;
    }
    form {
        width: 100%;
        flex-direction: column;
        display: flex;
    }
    select {
        width: 100%;
        margin: 5px;
    }
</style>
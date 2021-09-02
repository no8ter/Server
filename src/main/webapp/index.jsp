<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <a>Привет, ${sessionScope.userData.secondName} ${sessionScope.userData.firstName} ${sessionScope.userData.lastName}</a>
    <a href="/cab_redirect">Личный кабинет</a>
    <a href="/logout">Выход</a>
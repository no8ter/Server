<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<script>
    document.title = "Авторизация";
</script>
<body>
    <div class="main">
        <h2>Авторизация</h2>
        <div class="form">
            <form method="post">
                <label>
                    <input name="username" type="text" placeholder="Логин" required>
                </label>
                <label>
                    <input name="password" type="password" placeholder="Пароль" required>
                </label>
                <p><input type="submit" value="Войти"></p>
            </form>
        </div>
    </div>
</body>
<jsp:include page="footer.jsp" />

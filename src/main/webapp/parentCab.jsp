<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<script>
    document.title = "Кабинет родителя";
</script>
<body>
    <div class="main">
        <div class="form">
            <h2>Добавление заявки</h2>
            <form method="post">
                <p><label>
                    <input name="secondName" type="text" placeholder="Фамилия">
                </label></p>
                <p><label>
                    <input name="firstName" type="text" placeholder="Имя">
                </label></p>
                <p><label>
                    <input name="lastName" type="text" placeholder="Отчество">
                </label></p>
                <p><label>
                    <input name="birthday" type="date" placeholder="Дата рождения">
                </label></p>
                <p><label>
                    <input name="sex" type="text" placeholder="Пол">
                </label></p>
                <p><input type="submit" value="Отправить заявку"></p>
            </form>
        </div>
    </div>
</body>

<jsp:include page="footer.jsp" />

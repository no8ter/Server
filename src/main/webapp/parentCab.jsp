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
                <input name="secondName" type="text" placeholder="Фамилия" required>
                <input name="firstName" type="text" placeholder="Имя" required>
                <input name="lastName" type="text" placeholder="Отчество">
                <input name="birthday" type="date" placeholder="Дата рождения" required>
                <select name="sex">
                    <option value="М">Мужчина</option>
                    <option value="Ж">Женщина</option>
                </select>
                <p><input type="submit" value="Отправить заявку"></p>
            </form>
        </div>
    </div>
</body>

<jsp:include page="footer.jsp" />

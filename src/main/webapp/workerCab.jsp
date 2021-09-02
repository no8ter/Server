<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.rgrtu.pahomova.Utils.UserTools" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp" />
<script>
    document.title = "Кабинет родителя";
</script>
<body>
    <div class="main">
        <div class="form">
            <h2>Приём заявок</h2>
            <form method="post" action="/handleStudentRequest">
                <select name="listenerId">
                    <%
                        for (List<String> l: UserTools.getListenersList(false)) {
                            out.println("<option value=\""+l.get(0)+"\">"+l.get(1)+"</option>");
                        }
                    %>
                </select>
                <p><label>
                    <input type="radio" name="yes_no" value="true" checked><span>Принять</span>
                </label></p>
                <p><label>
                    <input type="radio" name="yes_no" value="false" ><span>Отклонить</span>
                </label></p>
                <input type="submit" value="Отправить">
            </form>
        </div>
    </div>
</body>
<jsp:include page="footer.jsp" />

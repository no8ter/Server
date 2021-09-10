<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.rgrtu.pahomova.Utils.UserTools" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp"/>
<script>
    document.title = "Кабинет учителя";
</script>
<body>
    <div class="main">
        <div class="form">
            <h2>Выставление оценок</h2>
            <form method="post" action="/handleSetMark">
                <select name="listenerId">
                    <%
                        for (List<String> l: UserTools.getListenersList(false)) {
                            out.println("<option value=\""+l.get(0)+"\">"+l.get(1)+"</option>");
                        }
                    %>
                </select>
                <select name="subjectId">
                    <%
                        for (List<String> l: UserTools.getSubjectsList()) {
                            out.println("<option value=\""+l.get(0)+"\">"+l.get(1)+"</option>");
                        }
                    %>
                </select>
                <select name="mark">
                    <%
                        for (int i=1; i<6; i++) {
                            out.println("<option value=\""+i+"\">"+i+"</option>");
                        }
                    %>
                </select>
                <input type="submit" value="Отправить">
            </form>
        </div>
    </div>
</body>
<jsp:include page="footer.jsp"/>
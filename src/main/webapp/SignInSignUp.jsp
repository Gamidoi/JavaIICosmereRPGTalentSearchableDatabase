<%@include file="includes/header.jsp" %>
<div style="align-content: center; width: 100vw;">
    <h1>You are not signed in. Please sign in to view your character.</h1>
    <div class="SignInSignUpFormsDiv">
        <form action="signIn" method="post">
            <h2>Sign In:</h2>
            <label>User Name:<input type="text" name="userName" required/></label>
            <br>
            <label>Password:<input type="password" name="userPassword" required/></label>
            <br>
            <input type="submit" />
        </form>
        <c:if test="${signInMessage != null}">
            <p>${signInMessage}</p>
        </c:if>
    </div>
    <div class="SignInSignUpFormsDiv">
        <form action="signUp" method="post">
            <h2>Sign Up:</h2>
            <label>User Name:<input type="text" name="userName" required/></label>
            <br>
            <label>Password: <input type="password" name="userPassword1" required/></label>
            <br>
            <label>Password: <input type="password" name="userPassword2" required/></label>
            <br>
            <input type="submit" />
        </form>
        <c:if test="${signUpMessage != null}">
            <p>${signUpMessage}</p>
        </c:if>
    </div>
</div>
<%@include file="includes/footer.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cosmere RPG searchable org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking.Talent Database.</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/styles.css">
        <script>
            function goToCharPage(){
                let goTo = document.getElementById("goToCharPage");
                goTo.submit();
            }
        </script>
    </head>
    <body>
        <div class="navigationBar">
            <a href="index.jsp" class="headerNavigation">Main&nbspPage</a>
            <c:choose>
                <c:when test="${userName == null}">
                    <a href="SignInSignUp.jsp" class="headerNavigation">Character&nbspSheet</a>
                </c:when>
                <c:otherwise>
                    <form action="goToCharPage" id="goToCharPage">
                        <p onclick="goToCharPage()" class="headerNavigation">Character&nbspSheet</p>
                    </form>
                    <form action="signOut" method="post"><input type="submit" value="Sign Out" class="signOutButton"></form>
                </c:otherwise>
            </c:choose>
        </div>


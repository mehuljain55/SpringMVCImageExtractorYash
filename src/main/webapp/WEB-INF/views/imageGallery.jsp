<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image Gallery</title>
</head>
<body>
    <h1>Image Gallery</h1>

    <c:if test="${not empty images}">
        <div class="gallery">
            <c:forEach var="image" items="${images}">
                <div class="image-item" style="margin: 10px; display: inline-block;">
                    <h3>Image ID: ${image.id}</h3>
                    <!-- Use Base64 string for the image with correct MIME type -->
                    <img src="data:${image.mimeType};base64,${image.base64Image}" alt="User Image" width="300" />
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${empty images}">
        <p>No images available to display.</p>
    </c:if>
</body>
</html>

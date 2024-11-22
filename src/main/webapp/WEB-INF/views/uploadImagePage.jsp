<!DOCTYPE html>
<html>
<head>
    <title>Upload Image</title>
</head>
<body>
    <h1>Upload an Image</h1>
    <form action="uploadImage" method="POST" enctype="multipart/form-data">
        <input type="file" name="file" />
        <button type="submit">Upload</button>
    </form>


    <br>
    <a href="viewImages">View Uploaded Images</a>
</body>
</html>

# image-storage-ms

*  Create album

    curl --location 'localhost:8080/api/v1/albums' \
--header 'Content-Type: application/json' \
--data '{
"name":"imageAlbum"
}'

* Upload Image to the Album

   curl --location 'http://localhost:8080/api/v1/images/upload' \
--form 'images=@"/Users/choudhary/Desktop/Screenshot 2023-06-10 at 7.09.57 PM.png"'
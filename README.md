
 # Create Image Album Spring Boot app
You can use the sample project which I have in here.

git clone https://github.com/aanjna/image-storage-ms

# image-storage-ms

*  Create album

   curl --location 'localhost:8080/api/v1/albums' \
   --header 'Content-Type: application/json' \
   --data '{
   "name":"imageAlbum"
   }'

* Upload Image to the Album

  curl --location 'http://localhost:8080/api/v1/images/upload' \
  --form 'images=@"/Users/choudhary/Desktop/Screenshot.png"'



Create Docker image
Command to create docker image using Google JIB plugin

./mvnw com.google.cloud.tools:jib-maven-plugin:build -Dimage=gcr.io/$VMWare/image-storage-ms:v1

Run the Docker image
Command to run the docker image which we created in the previous step

docker run -ti --rm -p 8080:8080 gcr.io/$VMWare/image-storage-ms:v1

Login to the K8s Cluster
Command to login to the K8s cluster from Cloud Shell

gcloud container clusters get-credentials vmware-cluster-1 --zone  us-central1-a

* Kubernetes Commands
    List Pods
kubectl get pods

    List Deployments
kubectl get deployments

    List Services
kubectl get services

    Deploy an image
kubectl run image-storage-ms --image=gcr.io/$VMWare/image-storage-ms:v1 --port=8080

    Expose Load Balancer
kubectl expose deployment image-storage-ms --type=LoadBalancer

    Scale deployments
kubectl scale deployment image-storage-ms --replicas=3

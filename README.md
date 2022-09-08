# FootballApp32566_SpringBoot

This App has been created with the Framework SpringBoot

Author: André Burkhart, Matrikelnr. 32566

FootballApp created with SpringBoot to add matches and see the resulting team rank

# Start Application

Execute Class FootballApp32566Application in order to start the Application with Spring Boot

## Call Matches via URL:

All Matches:
```shell script
http://localhost:8080/api/matches
```

Single Match(ID is the parameter):
```shell script
http://localhost:8080/api/matches/1
```
## Call ranking via URL:

Call a sorted rank of teams:
```shell script
http://localhost:8080/api/stats/rank
```

Call an unsorted rank of teams:
```shell script
http://localhost:8080/api/stats/all
```

Call team in first place:
```shell script
http://localhost:8080/api/stats/first
```

Call team in last place:
```shell script
http://localhost:8080/api/stats/last
```

Call teamstatistik of a specific team (ID is the parameter)
```shell script
http://localhost:8080/api/stats/1
```

## Update, Delete and Add Matches via REST-API:

Open file „API_Test_FootballApp_Spring.http“ in Intellij and Execute HTTP-Requests with Run-Button:

![grafik](https://user-images.githubusercontent.com/98780769/189173605-ece41133-d733-45af-805f-3087cff355f5.png)


Add one Match:

![grafik](https://user-images.githubusercontent.com/98780769/189173626-d6eea232-cd06-4f80-97cd-60bbf75139dd.png)

Add list of Matches:

![grafik](https://user-images.githubusercontent.com/98780769/189173636-cbf21e47-5e4e-4ae1-90d4-59aeee820895.png)

Delete Match via Parameter ID:

![grafik](https://user-images.githubusercontent.com/98780769/189173657-ff215545-fdf1-466f-b033-43f1e50fdb0f.png)

Update a single Match with new Goal statistic:

![grafik](https://user-images.githubusercontent.com/98780769/189173672-5af494a3-6768-4c66-90b5-d139a8e03ebe.png)




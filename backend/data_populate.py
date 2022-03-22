import requests

lis = [{"fieldName": "Astro field" }]
for json_obj in lis:
    r = requests.post(url = "https://prof-student-matching.herokuapp.com/rsfield/",data=json_obj)  
    print(r)
    # print(r.json())
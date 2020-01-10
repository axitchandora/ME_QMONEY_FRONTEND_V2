import requests
import argparse
import json


# curl -X POST   http://localhost:8081/portfolio/bottom-performer/2019-10-01/2019-10-22   -H 'Content-Type: application/json'   -d '{
#    "id": 1,
#    "stocks": ["GOOGL", "AAPL", "COST"]
# }'

def end_point(stocks):
    url = "http://localhost:8081/portfolio/analyze"
    with open(stocks) as json_file:
        stocks = json.load(json_file)
        data = {
            "name": "Anand",
            "portfolioTrades": stocks
        }
        print(url, json.dumps(data, indent=4))
        headers = {'content-type': 'application/json'}
        resp = requests.post(data=json.dumps(data), url=url, headers=headers)
        print(resp)
        print(json.dumps(resp.json(), indent=4))

# python3 dashboard.py --stocks GOOGL,AAPL,COST,CSCO,MSFT --start-date 2019-10-10 --end-date 2019-10-31 --query bottom-performer
# python3 dashboard.py --stocks GOOGL,AAPL,COST,CSCO,MSFT --start-date 2019-10-10 --end-date 2019-10-31 --query top-performer
# python3 dashboard.py --stocks GOOGL,AAPL,COST,CSCO,MSFT --start-date 2019-10-10 --end-date 2019-10-31 --query highest-volatile
# python3 dashboard.py --stocks GOOGL,AAPL,COST,CSCO,MSFT --start-date 2019-10-10 --end-date 2019-10-31 --query lowest-volatile

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--json', help='Json file to use for calculation', required=True)


    args = parser.parse_args()

    #["GOOGL", "AAPL", "COST"]
    print(args)


    end_point(args.json)

Ads Bidding Platform - demo

- Auctioneer
- Bidders (hosted on in a docker container)

How to run:
- run yieldlab-rtb-task/test-setup.sh (this should start bidders in a docker container)
- run auctionner application on local env (port 8080)

Testing:
- once bidders and auctioneer services are running, run yieldlab-rtb-task\run-test.sh and validate results. Note that this script has been modified to accommodate the implementation.

Simple archirectural overview of the system at: resources\ad-bidding-basic-architecture.drawio.png

Author: Velimir Avramovski
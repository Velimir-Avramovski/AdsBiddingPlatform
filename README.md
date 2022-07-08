Ads Bidding Platform - demo

### Services:
- Auctioneer
- Bidders (hosted on in a docker container)

### How to run:
- Run yieldlab-rtb-task/test-setup.sh (this should start bidders services as docker containers)
- Build (gradle clean build bootRun -Dbidders="http://localhost:8081, http://localhost:8082, http://localhost:8083") & Run auctionner application on local env (port 8080). 
Or Intellij run configuration: '.run\AdsBiddingPlatform_auctioneer [clean build bootRun].run.xml'.

### Testing:
- Once bidders and auctioneer services are running, run 'yieldlab-rtb-task\run-test.sh' and validate results. Note that this script has been modified to accommodate the implementation.

### Architectural view:
- Simple architectural overview of the system at: 'resources\ad-bidding-basic-architecture.drawio.png'

--------------------------------------------------------------------------------------------------------------------------------------------
Author: Velimir Avramovski; Email: velimir.avrv@gmail.com
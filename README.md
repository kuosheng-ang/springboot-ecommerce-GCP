

Features
--------
 - Products Catalogue that display collection of product items for purchase.
 - User Login Page Portal that incorporates IAM (Identity Access Management) that redirects page access for users 
   according to roles, permission and privilege. 
   
   ![User Roles Database Structure](/screenshots/users_roles_maintenance.PNG "User Roles Database Table")
   
   ![roles Privilege Database Structure](/screenshots/roles_privileges_maintenance.PNG "Roles Privilege Table")
   
 - Shopping Cart that contains products added for checkout before it's translated to transactional order.
 - Upon login/sign-in, app is configured that incorporates Session-based Management authentication server that will create a session for 
   the user after the user logs in. The session id is then stored on a cookie on the user’s browser.
  

### Users perspective

- Easy login & registration
- User-friendly product filtering
- Product gallery
- Shopping cart
- Order summary
- Payments using Paypal

### Admin perspective
- Easy login
- Mange or administer Pages (add, edit, delete, sort)
- Mange or administer Products (add, edit, delete)
- Mange or administer Categories (add, edit, delete, sort)


### User or Customer perspective

- Shopping Cart
  - Payment checkout items listed on shopping cart
 
    ![shopping cart screenshots to checkout for payment](/screenshots/shopping_cart.PNG "shopping cart screenshots")

- Customer Profile Management
  - Shipping Address
  
  ![customer shipping address management (ie: edit / delete )](/screenshots/customer_shipping_address.PNG "customer shipping address")

  - Order History
  - Account Management

    ![User/Customer profile identity details (ie: phone number, password)](/screenshots/customer_profile.PNG "Customer Profile")
    
  - User Registration
    - Include forms validation checks on the user inputs (ie: phone number should contain numeric values, firstname or lastname should not contain numeric values,     
      password matching, email input should contain @ & dot (ie: xxxx@hotmail.com ) & so forth.
      In this way, it minimizes any back-end DB server side or data model validation throw-back errors.
      
      
    ![ User Registration (forms validation) features as input control](/screenshots/registration_form_validation.PNG "User Registration")
      
    

Used Tools
-----------
- Java 11
- Spring Boot Framework v2.5.8
- PostgreSQL Version 14
- Thymeleaf Spring5 Security (sec:authorize="isAuthenticated()" / "isAnonymous()", "userAuthenticated") / 3.0.12.RELEASE
- Spring Security and roles
- Spring Data Jpa
- Spring Session

Database Setup
---------------
 - Database configuration setting tested on PostgreSQL version 14
 - Database Configuration in Spring Boot
   - ![database configuration on spring boot](/screenshots/DataBASE_configuration.PNG "database configuration on spring")

    

Project Setup
-------------
- Clone and open in IntelliJ (other IDE is also fine, make sure that spring boot plugins is installed)
- Change database connection config in `src/main/resources/application.properties`
- Install maven dependencies using IDE auto import or using the command ``mvn install``
- Run the app using ``mvn spring-boot:start`` from project root directory.

WebSite hosted on GCP - Google Cloud Platform:

  ![Main Page](/screenshots/mainPage.PNG "Main Page hosted on GCP - Google Cloud Platform")


**AWS Infrastrucure as a code (IaaC) implementation**:
- Scale my infrastructure worldwide and manage resources across all AWS accounts and regions through a single operation.
- Extend and manage my infrastructure to include cloud resources published in the CloudFormation Registry, the developer community, and my library.
- Automate resource management across my organization within the region with AWS service integrations offering turnkey application distribution and governance controls.

- click on the link to download the file ![AWS CloudFormation](/AWSCloudFormation_Deployment%20as%20IaaC.yaml "CloudFormation-IaaC ")  

  ![AWS CloudFormation](/screenshots/AWS-CloudFormation_Events.PNG "Using AWS CloudFormation script to implement as IaaC")

  ![AWS CloudFormation](/screenshots/AWS-CloudFormation_resources.PNG "Using AWS CloudFormation script to implement as IaaC")

- AWS Cloudformation can be enhanced by adding ALB, targetGroup, Auto-Scaling to establish AWS Web Infrastructure. Feel free to contact me: kuosheng.ang@outlook.com

  - AWS Architecture to emulate On-Premise Infrastructure Network when using Site-to-Site VPN Connection (ie: Openswan Protocol which offers IPSec capability/features) 

  ![AWS On-Premise infrastructure](/screenshots/AWS-Architecture-Diagram-Private-Cloud-On-Premise-Network.PNG "On-Premise Infrastructure network")
  

if this link below is showing 404 error, my apologies is that my Google Cloud account has exceeded free tier limited allocated

- https://8080-cs-656bed13-8b9b-4e14-a8da-28349f3ac58b.cs-asia-southeast1-ajrg.cloudshell.dev/?authuser=0&redirectedPreviously=true
- https://8080-cs-656bed13-8b9b-4e14-a8da-28349f3ac58b.cs-asia-southeast1-ajrg.cloudshell.dev/

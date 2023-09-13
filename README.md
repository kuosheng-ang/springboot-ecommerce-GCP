

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
      
###  Web-Security implemented using Spring Security Configuration

 -  Protecting against CSRF attacks


            @Bean

            public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

             http
    
            .csrf().disable();
    
            return http.build();
           }
  ![ Spring Security Configuration - Protecting against CSRF attacks](/screenshots/spring-security.PNG "Protecting against CSRF attacks")
    
- Spring provides two mechanisms to protect against CSRF attacks:

   - The Synchronizer Token Pattern 
   - Specifying the SameSite Attribute on your session cookie
     
- Synchronizer Token Pattern
The predominant and most comprehensive way to protect against CSRF attacks is to use the Synchronizer Token Pattern. This solution is to ensure that each HTTP request requires, in addition to our session cookie, a secure random generated value called a CSRF token be present in the HTTP request.

When an HTTP request is submitted, the server must look up the expected CSRF token and compare it against the actual CSRF token in the HTTP request. If the values do not match, the HTTP request should be rejected.

The key to this working is that the actual CSRF token should be in a part of the HTTP request that is not automatically included by the browser. For example, requiring the actual CSRF token in an HTTP parameter or an HTTP header will protect against CSRF attacks. Requiring the actual CSRF token in a cookie does not work because cookies are automatically included in the HTTP request by the browser.

     ### Synchronizer Token Form
     
     <form method="post"
          	action="/transfer">
          <input type="hidden"
          	name="_csrf"
          	value="4bfd1575-3ad1-4d21-96c7-4ef2d9f86721"/>
          <input type="text"
          	name="amount"/>
          <input type="text"
          	name="routingNumber"/>
          <input type="hidden"
          	name="account"/>
          <input type="submit"
          	value="Transfer"/>
    </form>
    
 - We can relax the expectations to require only the actual CSRF token for each HTTP request that updates the state of the application. For that to work, our application must ensure that safe HTTP methods are idempotent. This improves usability, since we want to allow linking to our website from external sites. Additionally, we do not want to include the random token in HTTP GET, as this can cause the tokens to be leaked.

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

- AWS Cloudformation can be enhanced by adding ALB (Application Load Balancer) with the defined TargetGroup, Auto-Scaling to establish high availability Web Infrastructure.
  - Feel free to contact me: kuosheng.ang@outlook.com
 
  - AWS Architecure for overall hybrid connectivty using Site-to-Site VPN Connection (Openswan Protocol which offers IPSec capability/features)

  ![AWS hybrid infrastructure](/screenshots/AWS-Architecture-Diagram_Netflix_Web-Architecture-Web-Cloud.PNG "Overall hybrid On-Premise & Cloud Infrastructure network")
    

  - AWS Architecture to emulate On-Premise Infrastructure Network when using Site-to-Site VPN Connection (ie: Openswan Protocol which offers IPSec capability/features)
 

  ![AWS On-Premise infrastructure](/screenshots/AWS-Architecture-Diagram-Private-Cloud-On-Premise-Network.PNG "On-Premise Infrastructure network")
  


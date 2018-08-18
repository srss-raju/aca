"use strict";

 angular.module('config', [])

.constant('ENV', {name:'dev',endPoint:'',domain:'',unicornContext:'/unicorn',acaContext:'/',unicornApplicationName:'1095B',unicornRolesFilter:'1095B_External',unicornEnterpriseAdminRoleName:'EnterpriseAdmin',unicornSuperAdminRoleName:'SuperAdmin',unicornRolePreFix:'1095B_',caseworker_read_write:'Caseworker ReadWrite',caseworker_read_only:'Caseworker ReadOnly',state_admin:'State Administrator',login:'./saml/login/alias/ACA1095BSP-DD_EntityAlias',logout:'./saml/logout/alias/ACA1095BSP-DD_EntityAlias',api:'/api'})

.constant('captcha', {siteKey:'6Lf5GRITAAAAAEXlH5gp2INuPMDVriqX3ARHOet_'})

; 


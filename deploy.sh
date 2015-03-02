# Script is used to copy our ebay.war file, remove existing directory, and compile
#rm -rf /var/lib/tomcat7/webapps/eBay/ && rm /var/lib/tomcat7/webapps/eBay.war
ant build && ant deploy
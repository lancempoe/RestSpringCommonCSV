# Streaming Rest API
I have build a sample api that streams back a csv file 
to the users as it is being build. This can process **big data** 
without worrying about memory usage. The data is flushed 
back to the user after a set amount of lines are created. 
I used the following:
- org.glassfish.jersey _2_
- org.springframework _4_
- org.apache.commons.commons-csv _1.4_
- StreamingOutput: javax.ws.rs-api _2.0.1_

In this project I have also played with difference testing frameworks
- org.jmockit _1.24_
- junit _4.10_
- org.hamcrest.hamcrest-junit _2.0.0.0_
- org.mockito.mockito-core _1.10.19_
- org.powermock _1.6.6_


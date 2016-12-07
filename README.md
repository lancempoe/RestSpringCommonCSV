# Streaming Rest API
I have build a sample api that streams back a csv file 
to the users as it is being build. This can process **big data** 
without worrying about memory usage. The data is flushed 
back to the user after a set amount of lines are created. I 
have built out a Grizzly implement so that this can be ran
outside of a web application server. I'm also leveraging a
linter for build.gradle

I used the following:
- Jersey 2
- Spring 4
- org.apache.commons.commons-csv _1.4_
- StreamingOutput: javax.ws.rs-api _2.0.1_

I'm building with Gradle:
- gradle _3.2.1_

In this project I have also played with difference testing frameworks
- org.jmockit _1.24_
- junit _4.10_
- org.hamcrest _2.0.0.0_


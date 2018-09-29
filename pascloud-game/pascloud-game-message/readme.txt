brew install automake
brew install libtool
brew install protobuf

解决protobuf-API版本冲突
有的时候，我们会遇到protobuf版本冲突。 
比如，我们的工程用到protobuf3.2版本，而引用到的一些库用到了protobuf 2.5版本。
两个版本的一些api冲突，可能造成意想不到的问题。我们可以利用shade插件，
把protobuf-API全部替换成私有的包名，避免命名冲突。

<!--  shade protobuf to avoid version conflicts -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>2.4.2</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <relocations>
                    <relocation>
                        <pattern>com.google.protobuf</pattern>
                        <shadedPattern>${project.groupId}.${project.artifactId}.shaded.protobuf</shadedPattern>
                    </relocation>
                </relocations>
            </configuration>
        </execution>
    </executions>
</plugin>


在项目中，需要把

import com.google.protobuf.*;

替换成

import groupId.artifactId.shaded.protobuf.*;
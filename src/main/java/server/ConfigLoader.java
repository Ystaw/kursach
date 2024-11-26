package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    public Properties getProps() throws IOException {  //сообщает нам, чтобы получить реквизит для базового элемента привязки
        Properties props=new Properties();

            props.load(getClass().getResourceAsStream("/serv.properties"));
            return props;


    }
}

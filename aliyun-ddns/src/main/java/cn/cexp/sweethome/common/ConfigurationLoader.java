package cn.cexp.sweethome.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ConfigurationLoader<T> {
    private static Logger log = LoggerFactory.getLogger(ConfigurationLoader.class); 
    private List<T> confList;
    private Class<T> elementClass;
    private String confFile;
    private String content;
    
    public ConfigurationLoader(Class<T> elementClass) {
    	this.elementClass = elementClass;
    }
    
    public void load() {
        try {
            readFile();
        } catch(IOException e) {
            log.error("Configureation load error.", e);
            return;
        }
        confList = JSON.parseArray(content, elementClass);
    }
    
    private void readFile() throws IOException {
        content = null;
        FileReader fileReader = new FileReader(confFile);
        BufferedReader reader = new BufferedReader(fileReader);
        StringBuilder contentBuilder = new StringBuilder();
        try {
            String line = reader.readLine();
            while(null != line) {
                contentBuilder.append(line);
                line = reader.readLine();
            }
        } finally {
            if(null != fileReader) {
                fileReader.close();
            }
        }
        content = contentBuilder.toString();
    }
    
    public T getConfUnique() {
    	if(null != confList && 0 != confList.size()) {
    		return confList.get(0);
    	}
    	return null;
    }
    
    public void load(String confFile) {
        setConfFile(confFile);
        load();
    }

    public List<T> getConfList() {
        return confList;
    }

	public Class<T> getElementClass() {
		return elementClass;
	}

	public void setElementClass(Class<T> elementClass) {
		this.elementClass = elementClass;
	}

	public String getConfFile() {
        return confFile;
    }

    public void setConfFile(String confFile) {
        this.confFile = confFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

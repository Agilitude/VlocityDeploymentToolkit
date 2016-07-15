package client.cmt11x;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Derek on 13/07/2016.
 */
public class DataPackRequest {
    public String processType;
    public RequestDetails processData;

    public class RequestDetails {
        public String VlocityDataPackType;
        public String VlocityDataPackId;
        public LinkedTreeMap VlocityDataPackData;
    }

    public DataPackRequest(String operation, String vlocityDataPackType) {
        this.processType = operation;
        this.processData = new RequestDetails();
        this.processData.VlocityDataPackType = vlocityDataPackType;
    }

    public DataPackRequest(String operation, String vlocityDataPackType, String vlocityDataPackDataId) {
        this.processType = operation;
        this.processData = new RequestDetails();
        this.processData.VlocityDataPackType = vlocityDataPackType;
        setDataPackDataId(vlocityDataPackDataId);
    }

    public void setDataPackContent(String dataPackContent) {
        Gson gson = new Gson();
        this.processData.VlocityDataPackData = gson.fromJson(dataPackContent, LinkedTreeMap.class);
    }

    public void setDataPackDataId(String id) {
        setDataPackContent("{\"Id\" : \"" + id + "\"}");
    }
}

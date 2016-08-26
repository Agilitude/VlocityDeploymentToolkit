package client.ins12x;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Derek on 13/07/2016.
 */
public class DataPackRequest extends client.DataPackRequest {
    public String processType;
    public RequestDetails processData;

    private String dataPackDataId;

    public class RequestDetails {
        public String VlocityDataPackType;
        public String VlocityDataPackId;
        public LinkedTreeMap VlocityDataPackData;
    }

    public DataPackRequest(client.DataPackRequest.RequestTypeEnum requestType, String vlocityDataPackType) {
        this.processType = requestType.name();
        this.processData = new RequestDetails();
        this.processData.VlocityDataPackType = vlocityDataPackType;
    }

    public DataPackRequest(client.DataPackRequest.RequestTypeEnum requestType, String vlocityDataPackType, String vlocityDataPackDataId) {
        this.processType = requestType.name();
        this.processData = new RequestDetails();
        this.processData.VlocityDataPackType = vlocityDataPackType;
        setDataPackDataId(vlocityDataPackDataId);
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

    @Override
    public String getDataPackDataId() {
        return this.dataPackDataId;
    }

    @Override
    public void setDataPackDataId(String dataPackDataId) {
        this.dataPackDataId = dataPackDataId;
        setDataPackContent("{\"Id\" : \"" + dataPackDataId + "\"}");
    }

    @Override
    public String getDataPackId() {
        return this.processData.VlocityDataPackId;
    }

    @Override
    public void setDataPackId(String dataPackId) {
        this.processData.VlocityDataPackId = dataPackId;
    }
}

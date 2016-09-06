package client;

/**
 * Created by Derek on 26/08/2016.
 */
public abstract class DataPackRequest {

    public abstract String getDataPackDataId();
    public abstract void setDataPackDataId(String dataPackDataId);
    public abstract String getDataPackId();
    public abstract void setDataPackId(String dataPackId);

    public enum RequestTypeEnum {
        Export
    }

}

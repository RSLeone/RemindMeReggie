package main;

public enum Returns {
    
    SUCCESS (0), INVALID_EVENT_NAME (-1), INVALID_EVENT_TYPE (-2), INVALID_SEVERITY_LEVEL (-3), EVENT_DOES_NOT_EXIST (-4), 
        NO_EVENTS_TO_DISPLAY (-5), 
        INVALID_USERNAME (-101), INVALID_PASSWORD (-102), COULD_NOT_HASH_PASSWORD (-103), COULD_NOT_LOGIN_WITH_ENTERED_CREDENTAILS (-104),
        COULD_NOT_SAVE_PROFILE (-105), USERNAME_ALREADY_TAKEN (-106), OLD_USERNAME_FILE_NOT_DELETED (-107);

    private int returnCode;

    Returns(int returnCodeValue)
    {
        returnCode = returnCodeValue;
    }

    public int getReturnCode()
    {
        return returnCode;
    }

}

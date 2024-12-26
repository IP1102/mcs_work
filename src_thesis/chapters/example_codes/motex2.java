private synchronized void updateInstanceRemoteStatus() {
    InstanceInfo.InstanceStatus currentRemoteInstanceStatus = null;
    if (instanceInfo.getAppName() != null) {
        Application app = getApplication(instanceInfo.getAppName());
        if (app != null) {
            InstanceInfo remoteInstanceInfo = app.getByInstanceId(instanceInfo.getId());
            if (remoteInstanceInfo != null) {
                currentRemoteInstanceStatus = remoteInstanceInfo.getStatus();
            }
        }
    }
    if (currentRemoteInstanceStatus == null) {
        currentRemoteInstanceStatus = InstanceInfo.InstanceStatus.UNKNOWN;
    }
    onRemoteStatusChanged(lastRemoteInstanceStatus, currentRemoteInstanceStatus);
    lastRemoteInstanceStatus = currentRemoteInstanceStatus;
}

protected void onRemoteStatusChanged(InstanceInfo.InstanceStatus oldStatus, InstanceInfo.InstanceStatus newStatus) {
    // Publish event if an EventBus is available
    if (eventBus != null) {
        StatusChangeEvent event = new StatusChangeEvent(oldStatus, newStatus);
        eventBus.publish(event);
    }
}
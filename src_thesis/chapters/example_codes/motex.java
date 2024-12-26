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
    if (lastRemoteInstanceStatus != currentRemoteInstanceStatus) {
        try {
            if (eventBus != null) {
                StatusChangeEvent event = new StatusChangeEvent(lastRemoteInstanceStatus,
                        currentRemoteInstanceStatus);
                eventBus.publish(event);
            }
        } finally {
            lastRemoteInstanceStatus = currentRemoteInstanceStatus;
        }
    }
  }
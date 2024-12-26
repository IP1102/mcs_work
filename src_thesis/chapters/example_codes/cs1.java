@Override
public EncryptionMaterialsProvider getProvider(final String materialName, final long version) {
    final Map<String, AttributeValue> ddbKey = new HashMap<String, AttributeValue>();
    ddbKey.put(DEFAULT_HASH_KEY, new AttributeValue().withS(materialName));
    ddbKey.put(DEFAULT_RANGE_KEY, new AttributeValue().withN(Long.toString(version)));
    final Map<String, AttributeValue> item = ddbGet(ddbKey);
    if (item == null || item.isEmpty()) {
        throw new IndexOutOfBoundsException("No material found: " + materialName + "#" + version);
    }
    return decryptProvider(item);
}

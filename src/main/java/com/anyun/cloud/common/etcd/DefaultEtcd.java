package com.anyun.cloud.common.etcd;
import com.anyun.cloud.common.exception.EtcdException;
import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.Lease;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;
import com.coreos.jetcd.lease.LeaseGrantResponse;
import com.coreos.jetcd.options.GetOption;
import com.coreos.jetcd.options.PutOption;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 08/12/2017
 */
public class DefaultEtcd implements Etcd {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEtcd.class);
    private Client client;
    private KV kvClient;
    private Lease lease;

    @Inject
    public DefaultEtcd(EtcdConfig etcdConfig) {
        String address = etcdConfig.getUrl();
        client = Client.builder().endpoints(address).build();
        this.kvClient = client.getKVClient();
        this.lease = client.getLeaseClient();
    }


    @Override
    public long grantAndKeepAliveLease(long ttlSecond) throws EtcdException {
        long leaseId = granLease(ttlSecond);
        lease.keepAlive(leaseId);
        LOGGER.info("keep-alive etcd lease id [{}]", leaseId);
        return leaseId;
    }

    @Override
    public long granLease(long ttlSecond) throws EtcdException {
        CompletableFuture<LeaseGrantResponse> grantFuture = lease.grant(ttlSecond);
        long leaseId;
        try {
            leaseId = grantFuture.get().getID();
        } catch (Exception e) {
            throw new EtcdException(e);
        }
        LOGGER.info("grant etcd lease id [{}]", leaseId);
        return leaseId;
    }

    @Override
    public void putKeyValueByLease(long leaseId, String key, String value) throws EtcdException {
        ByteSequence testPutKey = ByteSequence.fromString(key);
        ByteSequence testPutValue = ByteSequence.fromString(value);
        PutOption option = PutOption.newBuilder().withLeaseId(leaseId).build();
        CompletableFuture<PutResponse> putFuture = kvClient.put(testPutKey, testPutValue, option);
        try {
            putFuture.get();
        } catch (Exception e) {
            throw new EtcdException(e);
        }
        LOGGER.debug("put key [{}] value [{}]", key, value);
    }

    @Override
    public boolean isKeyExist(String key) throws EtcdException {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        GetOption option = GetOption.newBuilder().withKeysOnly(true).build();
        try {
            GetResponse response = kvClient.get(keyByteSequence, option).get();
            List<KeyValue> keyValues = response.getKvs();
            if (keyValues == null || keyValues.size() == 0)
                return false;
            return true;
        } catch (Exception e) {
            throw new EtcdException(e);
        }
    }

    @Override
    public List<KeyValue> getWithPrefix(String key, boolean keysOnly) throws EtcdException {
        ByteSequence keyByteSequence = ByteSequence.fromString(key);
        GetOption option = GetOption.newBuilder().withPrefix(keyByteSequence).withKeysOnly(keysOnly).build();
        try {
            return kvClient.get(keyByteSequence, option).get().getKvs();
        } catch (Exception e) {
            throw new EtcdException(e);
        }
    }
}

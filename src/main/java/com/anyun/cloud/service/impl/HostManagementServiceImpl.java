package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.common.tools.IP2Long;
import com.anyun.cloud.dao.HostManagementDao;
import com.anyun.cloud.dao.IpBlockDao;
import com.anyun.cloud.dao.impl.AbstractIciqlDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.UnregisteredHostsDto;
import com.anyun.cloud.model.dto.host.*;
import com.anyun.cloud.model.entity.*;
import com.anyun.cloud.service.HostManagementService;
import com.coreos.jetcd.data.KeyValue;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/24 11:27
 */
public class HostManagementServiceImpl extends AbstractIciqlDao implements HostManagementService {
    private final static Logger LOGGER = LoggerFactory.getLogger(HostManagementService.class);
    private HostManagementDao hostManagementDao;
    private IpBlockDao ipBlockDao;
    private Context context;
    private Response response;
    static Etcd etcd;


    @Inject
    public HostManagementServiceImpl(Context context, Database database) {
        super(database);
        this.context = context;
        this.hostManagementDao = context.getBeanByClass(HostManagementDao.class);
        this.ipBlockDao = context.getBeanByClass(IpBlockDao.class);
        this.etcd = context.getEtcd();

    }

    /**
     * 获取已注册宿主机列表
     *
     * @param id
     * @return
     */
    @Override
    public Response getRegisteredHosts(int id) {

        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<HostBaseEntity>();
            HostBaseEntity hostBaseEntity = hostManagementDao.selectDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostBaseEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;

    }

    /**
     * 获取已注册宿主机LXD信息
     *
     * @param id
     * @return
     */
    @Override
    public Response getHostLxdDetails(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<HostLxdEntity>();

            HostLxdEntity hostLxdEntity = hostManagementDao.selectLxdDetailsById(id);

            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostLxdEntity);

        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    /**
     * 获取宿主机docker信息
     *
     * @param id
     * @return
     */
    @Override
    public Response getHostDockerDetails(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<HostDockerEntity>();
            HostDockerEntity hostDockerEntity = hostManagementDao.selectDockerDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostDockerEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    /**
     * 获取宿主机软件信息
     *
     * @param id
     * @return
     */
    @Override
    public Response getHostSoftwareDetails(int id) {

        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<HostSoftwareEntity>();
            HostSoftwareEntity hostSoftwareEntity = hostManagementDao.selectSoftwareDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostSoftwareEntity);

        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }

    /**
     * 获取宿主机硬件信息
     *
     * @param id
     * @return
     */
    @Override
    public Response getHostHardwareDetails(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }

        try {
            response = new Response<HostHardwareEntity>();
            HostHardwareEntity hostHardwareEntity = hostManagementDao.selectHardwareDetailsById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostHardwareEntity);
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;

    }

    /**
     * 查询未注册宿主机列表
     *
     * @return
     */
    @Override
    public Response getUnregisteredHosts() {
        List<Host> list = getHosts();
        LOGGER.debug("list------------------------------------------------:[{}]", list);
        List<UnregisteredHostsDto> l = new ArrayList<>();
        list.forEach(x -> {
            HostBaseEntity b = hostManagementDao.selectHostBaseEntityByHostIp(x.getAddress());
            if (b == null) {
                UnregisteredHostsDto u = new UnregisteredHostsDto();
                u.setName(x.getName());
                u.setAddress(x.getAddress());
                u.setHost(x.getHost());
                l.add(u);
            }
        });
        if (l.size() == 0) {
            response = new Response<List<UnregisteredHostsDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "没有未注册宿主机");
            return response;
        } else {
            response = new Response<List<UnregisteredHostsDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(l);
            return response;
        }
    }
    /**
     * 注册宿主机
     *
     * @param ip
     * @return
     */
    @Override
    public Response registeredHosts(String ip) {
        HostBaseEntity b = hostManagementDao.selectHostBaseEntityByHostIp(ip);
        List<IpBlockEntity> list1;
        String sql = "select * from ip_block";
        LOGGER.debug("sql[{}]", sql);
        ResultSet rs = db.executeQuery(sql);
        list1 = db.buildObjects(IpBlockEntity.class, rs);
        LOGGER.debug("list1:[{}]：", list1);
        LOGGER.debug("b:[{}]", b);
        if (b != null) {
            response = new Response<HostBaseEntity>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(b);
            return response;
        } else {
            List<Host> list = getHosts();
            LOGGER.debug("b:[{}]", list);
            for (Host h : list) {
                if (ip.equals(h.getAddress())) {
                    //宿主机基础信息注册
                    HostBaseEntity hostBaseEntity;
                    hostBaseEntity = new HostBaseEntity();
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date1 = sdf.format(date);
                    hostBaseEntity.setCreateDate(date1);
                    hostBaseEntity.setHostIp(h.getAddress());

                    //查询属于所属数据中心，环境，分类
                    String a = h.getAddress();
                    LOGGER.debug("a:[{}]", a);
                    long o = IP2Long.ipToLong(a);
                    LOGGER.debug("o:[{}]", o);
                    for (IpBlockEntity i : list1) {
                        long start = IP2Long.ipToLong(i.getBlockStartIp());
                        long end = IP2Long.ipToLong(i.getBlockEndIp());
                        if (o <= end && o >= start) {
                            hostBaseEntity.setBlockId(i.getBlockId());
                            IpPoolEntity m = new IpPoolEntity();
                            int pool = i.getPoolId();
                            hostBaseEntity.setPoolId(i.getPoolId());
                            LOGGER.debug("poolId________________________________________:[{}]", pool);
                            List<IpPoolEntity> data1 = db.from(m).where(m.poolId).is(pool).select();
                            LOGGER.debug("data1________________________________________:[{}]", data1);
                            for (IpPoolEntity cc : data1) {
                                int centerId =cc.getCenterId();
                                String centerName = cc.getCenterName();
                                hostBaseEntity.setCenterName(centerName);
                               // LOGGER.debug("centerName________________________________________:[{}]", centerName);
                                hostBaseEntity.setCenterId(centerId);
                                hostBaseEntity.setEnvironment(cc.getEnvironment());
                                hostBaseEntity.setCategory(cc.getCategory());
                            }
                            break;
                        }
                        hostBaseEntity.setCenterId(0);
                    }

                    hostBaseEntity = hostManagementDao.insert(hostBaseEntity);
                    int hostId = hostBaseEntity.getHostId();
                    response = new Response<HostBaseEntity>();
                    //宿主机lxd信息注册
                    HostLxdEntity hostLxdEntity;
                    hostLxdEntity = new HostLxdEntity();
                    hostLxdEntity.setHostIp(h.getAddress());
                    hostLxdEntity.setHostId(hostId);
                    hostLxdEntity.setLxdVersion(h.getSoftware().getLxd().getVersion());
                    //宿主机IP注册分配
                    IpUseEntity ipUseEntity = new IpUseEntity();
                    ipUseEntity.setIp(h.getAddress());
                    ipUseEntity.setDes("宿主机注册");
                    ipUseEntity.setCreateDate(date1);
                    ipUseEntity.setBlockId(hostBaseEntity.blockId);
                    ipUseEntity.setHostId(hostBaseEntity.hostId);
                    //宿主机Docker信息注册
                    HostDockerEntity hostDockerEntity;
                    hostDockerEntity = new HostDockerEntity();
                    hostDockerEntity.setHostId(hostId);
                    hostDockerEntity.setHostIp(h.getAddress());
                    hostDockerEntity.setBuildTime(h.getSoftware().getDocker().getClient().getBuildTime());
                    hostDockerEntity.setDockerApiVersion(h.getSoftware().getDocker().getClient().getApiVersion());
                    hostDockerEntity.setDockerMinAPIVersion(h.getSoftware().getDocker().getClient().getMinAPIVersion());
                    hostDockerEntity.setDockerVersion(h.getSoftware().getDocker().getClient().getVersion());
                    hostDockerEntity.setGitCommit(h.getSoftware().getDocker().getClient().getGitCommit());
                    hostDockerEntity.setGoVersion(h.getSoftware().getDocker().getClient().getGoVersion());

                    //宿主机软件信息注册
                    HostSoftwareEntity hostSoftwareEntity;
                    hostSoftwareEntity = new HostSoftwareEntity();
                    hostSoftwareEntity.setHostId(hostId);
                    hostSoftwareEntity.setHostIp(h.getAddress());
                    hostSoftwareEntity.setAgentVersion(h.getSoftware().getAgent().getVersion());
                    hostSoftwareEntity.setAgentGoVersion(h.getSoftware().getAgent().getGoVersion());
                    hostSoftwareEntity.setAgentBuildTime(h.getSoftware().getAgent().getBuildTime());
                    hostSoftwareEntity.setOsArch(h.getSoftware().getOs().getArch());
                    hostSoftwareEntity.setOsCodename(h.getSoftware().getOs().getCodename());
                    hostSoftwareEntity.setOsDescription(h.getSoftware().getOs().getDescription());
                    hostSoftwareEntity.setOsDistributor(h.getSoftware().getOs().getDistributor());
                    hostSoftwareEntity.setOsKernelName(h.getSoftware().getOs().getKernelName());
                    hostSoftwareEntity.setOsKernelVersion(h.getSoftware().getOs().getKernelVersion());
                    hostSoftwareEntity.setOsRelease(h.getSoftware().getOs().getRelease());
                    hostSoftwareEntity.setOsUptime(h.getSoftware().getOs().getUptime());
                    hostSoftwareEntity.setOsDistributor(h.getSoftware().getOs().getDistributor());
                    LOGGER.debug("hostSoftware---------------:[{}]", hostSoftwareEntity.asJson());
                    //宿主机硬件信息注册
                    HostHardwareEntity hostHardwareEntity;
                    hostHardwareEntity = new HostHardwareEntity();
                    hostHardwareEntity.setHostId(hostId);
                    hostHardwareEntity.setHostIp(h.getAddress());
                    hostHardwareEntity.setCpuBrandName(h.getHardware().getCpu().getBrandName());
                    hostHardwareEntity.setCpuCachelineBytes(String.valueOf(h.getHardware().getCpu().getCachelineBytes()));
                    String[] features = h.getHardware().getCpu().getFeatures();
                    String feature = Arrays.toString(features);
                    hostHardwareEntity.setFeatures(feature);
                    hostHardwareEntity.setCpuL1DataCache(String.valueOf(h.getHardware().getCpu().getL1DataCache()));
                    hostHardwareEntity.setCpuL2Cache(String.valueOf(h.getHardware().getCpu().getL2Cache()));
                    hostHardwareEntity.setCpuL3Cache(String.valueOf(h.getHardware().getCpu().getL3Cache()));
                    hostHardwareEntity.setCpuLogicalCores(String.valueOf(h.getHardware().getCpu().getLogicalCores()));
                    hostHardwareEntity.setCpuModelId(String.valueOf(h.getHardware().getCpu().getModelId()));
                    LOGGER.debug("familyId1:---------------------------------------------[{}]", h.getHardware().getCpu().getFamilyId());
                    hostHardwareEntity.setCpuFamilyId(String.valueOf(h.getHardware().getCpu().getFamilyId()));
                    LOGGER.debug("familyId2:---------------------------------------------[{}]", hostHardwareEntity.cpuFamilyId);
                    hostHardwareEntity.setCpuPhysicalCores(String.valueOf(h.getHardware().getCpu().getPhysicalCores()));
                    hostHardwareEntity.setCpuThreadsPerCore(String.valueOf(h.getHardware().getCpu().getThreadsPerCore()));
                    hostHardwareEntity.setCpuL1InstructionCache(String.valueOf(h.getHardware().getCpu().getL1InstructionCache()));
                    hostHardwareEntity.setMemorySwap(String.valueOf(h.getHardware().getMemory().getSwap()));
                    hostHardwareEntity.setMemoryVirtual(String.valueOf(h.getHardware().getMemory().getVirtual()));
                    LOGGER.debug("hostHardwareEntity-:[{}]", hostHardwareEntity.asJson());
                    hostLxdEntity = hostManagementDao.insert(hostLxdEntity);
                    response = new Response<HostLxdEntity>();
                    ipUseEntity = hostManagementDao.insert(ipUseEntity);
                    response = new Response<IpUseEntity>();
                    hostDockerEntity = hostManagementDao.insert(hostDockerEntity);
                    LOGGER.debug("hostDockerEntity:[{}]", hostDockerEntity.asJson());
                    response = new Response<HostDockerEntity>();
                    hostSoftwareEntity = hostManagementDao.insert(hostSoftwareEntity);
                    response = new Response<HostSoftwareEntity>();
                    hostHardwareEntity = hostManagementDao.insert(hostHardwareEntity);
                    response = new Response<HostHardwareEntity>();
                    response.setCode(ErrorCode.NO_ERROR.code());
                    hostBaseEntity.setLxdEntity(hostLxdEntity);
                    hostBaseEntity.setDockerEntity(hostDockerEntity);
                    hostBaseEntity.setSoftwareEntity(hostSoftwareEntity);
                    hostBaseEntity.setHardwareEntity(hostHardwareEntity);
                    response.setContent(hostBaseEntity);
                    //  response.setContent(ipUseEntity);
                    return response;
                }
            }
        }
        return response;
    }

    /**
     * 分页显示已注册宿主机列表
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Override
    public Response getPageList(int index, int limit, String sortBy, String sortDirection) {
        try {
            response = new Response<PageDto<ProductEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostManagementDao.selectPageList(index, limit, sortBy, sortDirection));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }

    }

    /**
     * 注销宿主机
     *
     * @param id
     * @return
     */
    @Override
    public Response cancel(int id) {
        LOGGER.debug("id:[{}]", id);
        if (id == 0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
        }
        try {
            response = new Response<HostBaseEntity>();
            hostManagementDao.cancelById(id);
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(null);
            return  response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.DELETE_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.DELETE_RESOURCE_ERROR.name() + e.getMessage());
        }
        return response;
    }


    /**
     * 根据条件查询宿主机
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param ip
     * @return
     */
    @Override
    public Response getHostsListByCondition(int index, int limit, String sortBy, String sortDirection, String ip, String environment, String category, String centerName) {
        try {
            response = new Response<PageDto<HostBaseEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostManagementDao.selectHostsListByCondition(index, limit, sortBy, sortDirection, ip, environment, category, centerName));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }


    /**
     * 从etcd获取未注册宿主机信息
     *
     * @return
     */
    @Override
    public List<Host> getHosts() {
        //传入参数key值
        List<KeyValue> list = etcd.getWithPrefix("/discovery/node/", true);
        List<String> hosts = new ArrayList<>();
        list.forEach(l -> {
            String s = new String(l.getKey().getBytes());
            LOGGER.debug("------s:[{}]", s);
            String host = "/discovery/node/" + s.split("/discovery/node/")[1].split("/")[0];
            LOGGER.debug("-----host:[{}]", host);
            if (!hosts.contains(host))
                hosts.add(host);
        });
        List<Host> hostList = new ArrayList<>();
        for (String h : hosts) {
            //宿主机硬件
            Hardware hardware = new Hardware();
            String hardwareString = new String(etcd.getWithPrefix(h + "/hardware", false).get(0).getValue().getBytes());
            Map<String, Object> hardwareMap = JsonUtil.fromJson(Map.class, hardwareString);
            Cpu cpu = JsonUtil.fromJson(Cpu.class, JsonUtil.toJson(hardwareMap.get("cpu")));
            Memory memory = JsonUtil.fromJson(Memory.class, JsonUtil.toJson(hardwareMap.get("memory")));
            hardware.setCpu(cpu);
            hardware.setMemory(memory);
            LOGGER.debug("cpu:--------------------------------------------------------[{}]", cpu.familyId);
            //宿主机软件
            Software software = new Software();
            String softwareString = new String(etcd.getWithPrefix(h + "/software", false).get(0).getValue().getBytes());
            Map<String, Object> softwareMap = JsonUtil.fromJson(Map.class, softwareString);
            Agent agent = JsonUtil.fromJson(Agent.class, JsonUtil.toJson(softwareMap.get("Agent")));
            OS os = JsonUtil.fromJson(OS.class, JsonUtil.toJson(softwareMap.get("OS")));
            LXD lxd = JsonUtil.fromJson(LXD.class, JsonUtil.toJson(softwareMap.get("LXD")));
            //宿主机软件Docker
            Docker docker = new Docker();
            Map<String, Object> dockerMap = JsonUtil.fromJson(Map.class, JsonUtil.toJson(softwareMap.get("Docker")));
            Client client = JsonUtil.fromJson(Client.class, JsonUtil.toJson(dockerMap.get("Client")));
            Server server = JsonUtil.fromJson(Server.class, JsonUtil.toJson(dockerMap.get("Server")));
            docker.setClient(client);
            docker.setServer(server);
            //宿主机软件Agent
            software.setAgent(agent);
            software.setDocker(docker);
            software.setLxd(lxd);
            software.setOs(os);
            Host host = new Host();
            host.setHost(h);
            host.setHardware(hardware);
            host.setSoftware(software);
            host.setName(new String(etcd.getWithPrefix(h + "/name", false).get(0).getValue().getBytes()));
            host.setAddress(new String(etcd.getWithPrefix(h + "/address", false).get(0).getValue().getBytes()));
            hostList.add(host);
        }
        LOGGER.debug("hostList________________________________________:[{}]", hostList);
        return hostList;
    }

    @Override
    public Response getHostsByCenterName(int centerId) {
     /*   LOGGER.debug("centerName:[{}]", centerId);
        if (centerId ==0) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "centerId is empty");
        }
*/
        try {
            response = new Response<PageDto<HostBaseEntity>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostManagementDao.selectHostsListByCenterName(centerId));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }

    }

    @Override
    public Response getUnregisteredHostsList(int index, int limit) {
        try {
            response = new Response<PageDto<UnregisteredHostsDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(hostManagementDao.selectHostsList(index, limit));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() +"没有未注册宿主机");
            return response;
        }
    }

}

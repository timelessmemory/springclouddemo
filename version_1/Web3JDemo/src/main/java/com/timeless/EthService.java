package com.timeless;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.*;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

/**
 * 初版 不完善 有问题 交易没带数据 智能合约调用有问题
 */
public class EthService {

    private static final String URL = "http://localhost:8000";

    public static Web3j initWeb3j() {
        return Web3j.build(getService());
    }

    private static HttpService getService(){
        return new HttpService(URL);
    }

    public static Admin initAdmin(){
        return Admin.build(getService());
    }

    public static Geth initGeth(){
        return Geth.build(getService());
    }

    /**
     * 根据密码创建账户
     *
     * @param password 密码
     * @return 账户
     * @throws IOException
     */
    public static String newAccount(String password) throws IOException {
        Admin admin = initAdmin();
        Request<?, NewAccountIdentifier> request = admin.personalNewAccount(password);
        NewAccountIdentifier newAccountIdentifier = request.send();
        return newAccountIdentifier.getAccountId();
    }

    /**
     * 获得当前区块编号
     *
     * @return 当前区块编号
     * @throws IOException
     */
    public static BigInteger getCurrentBlockNumber() throws IOException {
        Web3j web3j = initWeb3j();
        Request<?, EthBlockNumber> request = web3j.ethBlockNumber();
        EthBlockNumber ethBlockNumber = request.send();
        return ethBlockNumber.getBlockNumber();
    }

    /**
     * 根据区块编号获得EthBlock
     *
     * @param blockNumber
     * @return
     * @throws IOException
     */
    public static EthBlock getBlockEthBlock(Integer blockNumber) throws IOException {
        Web3j web3j = initWeb3j();
        DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(blockNumber);
        Request<?, EthBlock> request = web3j.ethGetBlockByNumber(defaultBlockParameter, true);
        EthBlock ethBlock = request.send();
        return ethBlock;
    }

    /**
     * 解锁账户，发送交易前需要对账户进行解锁
     *
     * @param accountId
     * @param password 密码
     * @param duration 解锁有效时间，单位秒
     * @return
     * @throws IOException
     */
    public static Boolean unlockAccount(String accountId, String password, BigInteger duration) throws IOException {
        Admin admin = initAdmin();
        Request<?, PersonalUnlockAccount> request = admin.personalUnlockAccount(accountId, password, duration);
        PersonalUnlockAccount account = request.send();
        return account.accountUnlocked();
    }

    /**
     * 锁定账户，使用完成之后需要锁定
     *
     * @param accountId
     * @return
     * @throws IOException
     */
    public static Boolean lockAccount(String accountId) throws IOException {
        Geth geth = initGeth();
        Request<?, BooleanResponse> request = geth.personalLockAccount(accountId);
        BooleanResponse response = request.send();
        return response.success();
    }

    public static BigInteger getBalance(String accountId) {
        try {
            Web3j web3j = initWeb3j();
            BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();
            DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(blockNumber);
            EthGetBalance ethGetBalance = web3j.ethGetBalance(accountId, defaultBlockParameter).send();

            if (ethGetBalance != null) {
                return ethGetBalance.getBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据hash值获取交易
     *
     * @param hash
     * @return
     * @throws IOException
     */
    public static EthTransaction getTransactionByHash(String hash) throws IOException {
        Web3j web3j = initWeb3j();
        Request<?, EthTransaction> request = web3j.ethGetTransactionByHash(hash);
        EthTransaction ethTransaction = request.send();
        return ethTransaction;
    }

    public static String transferEth(String fromAccount, String toAccount, String password, BigInteger value) throws Exception {
        Admin admin = initAdmin();
        EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(
                fromAccount, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAccount, password).send();

        if (personalUnlockAccount.accountUnlocked()) {
            BigInteger GAS_PRICE = BigInteger.valueOf(4000000000L);
            BigInteger GAS_LIMIT = BigInteger.valueOf(4712388);

            if (value == null) {
                value = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();
            }

            Transaction transaction = Transaction.createEtherTransaction(fromAccount, nonce, GAS_PRICE, GAS_LIMIT, toAccount, value);

            EthSendTransaction transactionResponse = admin.ethSendTransaction(transaction).sendAsync().get();;

            if (transactionResponse.hasError()) {
                String message = transactionResponse.getError().getMessage();
                System.out.println("transaction failed,info:" + message);
                return null;
            } else {
                String hash = transactionResponse.getTransactionHash();
                System.out.println("transaction from " + fromAccount + " to " + toAccount +" amount:" + value);
                return hash;
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getNonce("0x0aafa138a581a9e1537cfdcc15bdd49f997e2eb1"));
//        System.out.println(getBlockEthBlock(3).getResult().getSize());
//          String hash = transferEth("0x0aafa138a581a9e1537cfdcc15bdd49f997e2eb1", "0xce03a207835aa77f4325c4aae9637fe30f796a16", "123456", new BigInteger("1"));
//        System.out.println(hash);
        deploy("0x0aafa138a581a9e1537cfdcc15bdd49f997e2eb1", "123456");
//        load("0x0aafa138a581a9e1537cfdcc15bdd49f997e2eb1", "0xd7be03c559ea4409014154c94f35beceb69efbda", "123456");

    }

    public static String deploy(String accountId, String password) throws Exception {

        Web3j web3j = initWeb3j();
        Admin admin = initAdmin();
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(accountId, password).send();

        BigInteger GAS_PRICE = BigInteger.valueOf(4000000000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4712388);

        TransactionManager transactionManager = new ClientTransactionManager(web3j, accountId);

        RemoteCall<C__Users_Administrator_Desktop_Mrc_sol_Mrc> remoteCall = C__Users_Administrator_Desktop_Mrc_sol_Mrc.deploy(web3j, transactionManager, GAS_PRICE, GAS_LIMIT);

        C__Users_Administrator_Desktop_Mrc_sol_Mrc deploy = remoteCall.send();

        String address = deploy.getContractAddress();
        System.out.println(address);

        Utf8String utf8String = new Utf8String("{'name': 'lis'}");
        TransactionReceipt transactionReceipt = deploy.save(utf8String).send();
        System.out.println(transactionReceipt);
        System.out.println(deploy.get().send());

        return address;
    }

    /**
     * 使用一个现有的智能合约
     * @param accountId
     * @param address
     * @param password
     * @throws Exception
     */
    public static void load(String accountId, String address, String password) throws Exception {
        Web3j web3j = initWeb3j();
        Admin admin = initAdmin();
        BigInteger GAS_PRICE = BigInteger.valueOf(4000000000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4712388);

        TransactionManager transactionManager = new ClientTransactionManager(web3j, accountId);
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(accountId, password).send();
        C__Users_Administrator_Desktop_Mrc_sol_Mrc c_call = C__Users_Administrator_Desktop_Mrc_sol_Mrc.load(address, web3j, transactionManager, GAS_PRICE, GAS_LIMIT);

        if (c_call.isValid()) {
            Utf8String utf8String = new Utf8String("{'name': 'lis'}");
            TransactionReceipt transactionReceipt = c_call.save(utf8String).send();
            System.out.println(transactionReceipt);
        }
    }
}

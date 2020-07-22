package com.timeless;

import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import rx.Subscription;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 2版本 钱包
 * 可以上链
 */
public class EthService2 {

    private static final String URL = "http://localhost:8000";

    public static Web3j initWeb3j() {
        return Web3j.build(getService());
    }

    private static HttpService getService(){
        return new HttpService(URL);
    }

    /**
     * 创建账户
     * @param pwd
     * @throws Exception
     */
    public static void createAccount(String pwd) throws Exception {

        Bip39Wallet wallet = WalletUtils.generateBip39Wallet(pwd, new File("C:\\Users\\Administrator\\Desktop\\keystore\\"));

        Credentials credentials = WalletUtils.loadBip39Credentials(pwd, wallet.getMnemonic());

        //钱包地址 0x23f984a40d187d6c97b387b5436492023cceb12c
        String address = credentials.getAddress();
        System.out.println(address);
        //钱包私钥 89662c6e15987af2e26617a004b4b03ad57d477313352437acafdee61fc36660
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        System.out.println(privateKey);
        //钱包公钥 5911320c64817d704da0a208cde98546ce8de0d124bc287e995e99de468c560e94bba5c6fcdbdc530b5de103e1cc6aa17fb98f64d4c0a4fee30bd3fd5b529b42
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
        System.out.println(publicKey);

        String filenName = wallet.getFilename();//UTC--2020-07-22T01-26-33.79000000Z--23f984a40d187d6c97b387b5436492023cceb12c.json
        System.out.println(filenName);
        String mnemonic = wallet.getMnemonic();//rather cheap album catalog twenty move attack tube fossil purity thing voice
        System.out.println(mnemonic);
    }

    /**
     * 获取账户余额
     * @param address
     * @throws Exception
     */
    public static void getBalance(String address) throws Exception {

        Web3j web3j = initWeb3j();

        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

        if (ethGetBalance != null) {
            String banlance = ethGetBalance.getBalance().toString();
            System.out.println("账号余额(wei)：" + banlance);
            System.out.println("账号余额(eth)：" + Convert.fromWei(banlance, Convert.Unit.ETHER) + "ETH");
        }
    }

    /**
     * 转账
     * @param fromAccount
     * @param toAccount
     * @param value
     * @param privateKey
     * @param input 携带数据 上链
     * @throws Exception
     */
    public static void transfer(String fromAccount, String toAccount, BigInteger value, String privateKey, String input) throws Exception {

        Web3j web3j = initWeb3j();

        EthGetBalance ethGetBalance = web3j.ethGetBalance(fromAccount, DefaultBlockParameterName.LATEST).send();

        if (ethGetBalance != null && ethGetBalance.getBalance().compareTo(value) == 1) {
            //方法1私钥
            Credentials credentials = Credentials.create(privateKey);
            //方法2 Credentials credentials = WalletUtils.loadCredentials(passWord, filenName);

            BigInteger nonce = web3j.ethGetTransactionCount(fromAccount, DefaultBlockParameterName.LATEST).send().getTransactionCount();
            BigInteger GAS_PRICE = BigInteger.valueOf(4000000000L);
            BigInteger GAS_LIMIT = BigInteger.valueOf(4712388);

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE, GAS_LIMIT, toAccount, value, input);
            //签名
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

            if (ethSendTransaction.getError() != null) {
                System.out.println(ethSendTransaction.getError().getMessage());
            }
            String transactionHash = ethSendTransaction.getTransactionHash();

            System.out.println(transactionHash);
        }
    }

    /**
     * 获取交易信息
     * @param transactionHash
     * @throws Exception
     */
    public static void getTransaction(String transactionHash) throws Exception {
        Web3j web3j = initWeb3j();
        EthTransaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash).send();
        Transaction transaction = ethTransaction.getResult();
        System.out.println(HexTool.toString(transaction.getInput()));
        System.out.println(transaction);
    }

    /**
     * 监听交易
     * @throws Exception
     */
    public static void listen() throws Exception {
        Web3j web3j = initWeb3j();

        Subscription subscription = web3j.transactionObservable().subscribe(transaction -> {
            System.out.println("listen : " + transaction.getBlockHash());
        });
    }

    public static void main(String[] args) throws Exception {
//        createAccount("123456");
//        getBalance("0x23f984a40d187d6c97b387b5436492023cceb12c");

//        String input = HexTool.toHex("{'goodsName':'yifu', 'goodsPrice':900,'produceDate':'2020-06-10 20:02:00','color':['red','black','yellow'], 'city':{'first':'shanghai','second':'beijing'}}");
//        EthService.unlockAccount("0x23f984a40d187d6c97b387b5436492023cceb12c", "123456", new BigInteger("200"));
//        transfer("0x23f984a40d187d6c97b387b5436492023cceb12c", "0xce03a207835aa77f4325c4aae9637fe30f796a16", new BigInteger("1"), "89662c6e15987af2e26617a004b4b03ad57d477313352437acafdee61fc36660", input);
        listen();

//        getTransaction("0x71000e307c4c19e7e574663dff06bf803f5f5282b36a38512440dc0e200266f9");
//        getTransaction("0x82ab476c997438e8eca093268dc3fb0691851a4888cf4b325a50cdaa8fc8cd2a");

    }
}

package com.timeless;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class C__Users_Administrator_Desktop_Mrc_sol_Mrc extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506102dc806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806338e48f061461003b5780636d4ce63c146100e3575b600080fd5b6100e16004803603602081101561005157600080fd5b81019060208101813564010000000081111561006c57600080fd5b82018360208201111561007e57600080fd5b803590602001918460018302840111640100000000831117156100a057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610160945050505050565b005b6100eb610177565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561012557818101518382015260200161010d565b50505050905090810190601f1680156101525780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b805161017390600090602084019061020e565b5050565b60008054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156102035780601f106101d857610100808354040283529160200191610203565b820191906000526020600020905b8154815290600101906020018083116101e657829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024f57805160ff191683800117855561027c565b8280016001018555821561027c579182015b8281111561027c578251825591602001919060010190610261565b5061028892915061028c565b5090565b61020b91905b80821115610288576000815560010161029256fea26469706673582212201501375b14f9bd7b73b1d41db67ec6e20911b4f421d7fa7334083376b4aaac9f64736f6c634300060b0033";

    protected C__Users_Administrator_Desktop_Mrc_sol_Mrc(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected C__Users_Administrator_Desktop_Mrc_sol_Mrc(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> get() {
        final Function function = new Function(
                "get", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> save(Utf8String inparam) {
        final Function function = new Function(
                "save", 
                Arrays.<Type>asList(inparam), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<C__Users_Administrator_Desktop_Mrc_sol_Mrc> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(C__Users_Administrator_Desktop_Mrc_sol_Mrc.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<C__Users_Administrator_Desktop_Mrc_sol_Mrc> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(C__Users_Administrator_Desktop_Mrc_sol_Mrc.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static C__Users_Administrator_Desktop_Mrc_sol_Mrc load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new C__Users_Administrator_Desktop_Mrc_sol_Mrc(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static C__Users_Administrator_Desktop_Mrc_sol_Mrc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new C__Users_Administrator_Desktop_Mrc_sol_Mrc(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}

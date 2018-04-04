/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bitcoinj.params;

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.*;

import java.math.BigInteger;
import java.net.*;

import static com.google.common.base.Preconditions.*;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends AbstractBitcoinNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;

    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = new BigInteger("0000000fffffffffffffffffffffffffffffffffffffffffffffffffffffffff",16);
        		//Utils.decodeCompactBits(0x1d00ffffL);
        dumpedPrivateKeyHeader = 128;
        addressHeader = 0;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        port = 10666;
        //添加version
        
        packetMagic = 0xf9beb402L;
        bip32HeaderPub = 0x0488B21E; //The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderPriv = 0x0488ADE4; //The 4 byte header that serializes in base58 to "xprv"

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;
        //2018867506
        genesisBlock.setDifficultyTarget(2018867506);
        genesisBlock.setTime(1516820700);
        genesisBlock.setHash(Sha256Hash.wrap("00000006941e463cf1cb6c74024228810dc81545c854ba5153b117d3bf602204"));
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000;
        spendableCoinbaseDepth = 100;
     
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("00000006941e463cf1cb6c74024228810dc81545c854ba5153b117d3bf602204"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put(0, Sha256Hash.wrap("00000006941e463cf1cb6c74024228810dc81545c854ba5153b117d3bf602204"));
        checkpoints.put(111, Sha256Hash.wrap("0000000267a3a12da835680c963702dca940b1302883ea605669ad99881d06d4"));
        checkpoints.put(3000, Sha256Hash.wrap("000000016b6a454f4fef562024d4dbb822d1af76ce48352afc30b6892214394d"));
        checkpoints.put(4600, Sha256Hash.wrap("00000000e55dd5660ea3e64fa0bbcd2550369ac7f04479a3da94a29e44fe19a3"));
        checkpoints.put(4650, Sha256Hash.wrap("00000000120a2fdfe68d571f7c3eadaac1c1be8f18f15817e533e43eee32a120"));

        dnsSeeds = new String[] {
                "seed.cn1.genyuanlian.com",         // Pieter Wuille
                "seed.hk1.genyuanlian.com",          // Matt Corallo
                "seed.hk1.rSTK.world",   // Luke Dashjr
                "seed.cn1.rSTK.world",        // Chris Decker
        };
        httpSeeds = new HttpDiscovery.Details[] {
                // Andreas Schildbach
                new HttpDiscovery.Details(
                        ECKey.fromPublicOnly(Utils.HEX.decode("0238746c59d46d5408bf8b1d0af5740fe1a6e1703fcb56b2953f0b965c740d256f")),
                        URI.create("http://httpseed.bitcoin.schildbach.de/peers")
                )
        };

        addrSeeds = new int[] {
        		 0x45351c77,
       			 0x0d431c77,
       			 0xac657e76,
      			 0xc3ade66f,
      			 0x28a9e66f,
      			 0xe3893331,
      			 0x37c65ada,
      			 0xf65231fe,
      			 0xd257ea72,
      			 0xe617e76,
        };
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}

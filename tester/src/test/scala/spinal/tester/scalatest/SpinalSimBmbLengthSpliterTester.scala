package spinal.tester.scalatest

import org.scalatest.funsuite.AnyFunSuite
import spinal.core.sim.{SimConfig, SimTimeout}
import spinal.lib.bus.bmb.sim.BmbBridgeTester
import spinal.lib.bus.bmb.{BmbAccessParameter, BmbAlignedSpliter, BmbParameter, BmbSourceParameter}


class SpinalSimBmbLengthSpliterTester extends AnyFunSuite {
//  test("failed to stop case: only read " ) {
//    SimConfig.compile {
//      val c = BmbAlignedSpliter(
//        ip = BmbAccessParameter(
//          addressWidth = 16,
//          dataWidth = 32
//        ).addSources(16, BmbSourceParameter(
//          lengthWidth = 6,
//          contextWidth = 3,
//          alignmentMin = 0,
//          canRead = true,
//          canWrite = false,
//          alignment = BmbParameter.BurstAlignement.WORD
//        )).toBmbParameter(),
//        lengthMax = 4
//      )
//      c
//    }.doSimUntilVoid("test") { dut =>
//      new BmbBridgeTester(
//        master = dut.io.input,
//        masterCd = dut.clockDomain,
//        slave = dut.io.output,
//        slaveCd = dut.clockDomain,
//        alignmentMinWidth = dut.ip.access.alignmentMin
//      )
//    }
//  }

//  test("Error case: bypass_wr " ) {
//    SimConfig.compile {
//      val c = BmbAlignedSpliter(
//        ip = BmbAccessParameter(
//          addressWidth = 16,
//          dataWidth = 32
//        ).addSources(16, BmbSourceParameter(
//          lengthWidth = 6,
//          contextWidth = 3,
//          alignmentMin = 0,
//          canRead = true,
//          canWrite = true,
//          alignment = BmbParameter.BurstAlignement.WORD
//        )).toBmbParameter(),
//        lengthMax = 4
//      )
//      c
//    }.doSimUntilVoid("test") { dut =>
//      new BmbBridgeTester(
//        master = dut.io.input,
//        masterCd = dut.clockDomain,
//        slave = dut.io.output,
//        slaveCd = dut.clockDomain,
//        alignmentMinWidth = dut.ip.access.alignmentMin
//      )
//    }
//  }


  for(w <- List(true); r <- List(false, true);   if w || r) {
    val header = "_" + (if (w) "w" else "") + (if (r) "r" else "")
    test("bypass" + header) {
      SimConfig.compile {
        val c = BmbAlignedSpliter(
          ip = BmbAccessParameter(
            addressWidth = 16,
            dataWidth = 32
          ).addSources(16, BmbSourceParameter(
            lengthWidth = 6,
            contextWidth = 3,
            alignmentMin = 0,
            canRead = r,
            canWrite = w,
            alignment = BmbParameter.BurstAlignement.WORD
          )).toBmbParameter(),
          lengthMax = 4
        )
        c
      }.doSimUntilVoid("test") { dut =>
        new BmbBridgeTester(
          master = dut.io.input,
          masterCd = dut.clockDomain,
          slave = dut.io.output,
          slaveCd = dut.clockDomain,
          alignmentMinWidth = dut.ip.access.alignmentMin
        )
      }
    }

    test("8" + header) {
      SimConfig.withWave.compile {
        val c = BmbAlignedSpliter(
          ip = BmbAccessParameter(
            addressWidth = 16,
            dataWidth = 32
          ).addSources(16, BmbSourceParameter(
            lengthWidth = 6,
            contextWidth = 8,
            alignmentMin = 0,
            canRead = r,
            canWrite = w,
            alignment = BmbParameter.BurstAlignement.WORD
          )).toBmbParameter(),
          lengthMax = 8
        )
        c
      }.doSimUntilVoid("test", 42) { dut =>
        new BmbBridgeTester(
          master = dut.io.input,
          masterCd = dut.clockDomain,
          slave = dut.io.output,
          slaveCd = dut.clockDomain,
          alignmentMinWidth = dut.ip.access.alignmentMin
        )
      }
    }

    test("16" + header) {
      SimConfig.compile {
        val c = BmbAlignedSpliter(
          ip = BmbAccessParameter(
            addressWidth = 16,
            dataWidth = 32
          ).addSources(16, BmbSourceParameter(
            lengthWidth = 6,
            contextWidth = 8,
            alignmentMin = 0,
            canRead = r,
            canWrite = w,
            alignment = BmbParameter.BurstAlignement.WORD
          )).toBmbParameter(),
          lengthMax = 16
        )
        c
      }.doSimUntilVoid("test") { dut =>
        new BmbBridgeTester(
          master = dut.io.input,
          masterCd = dut.clockDomain,
          slave = dut.io.output,
          slaveCd = dut.clockDomain,
          alignmentMinWidth = dut.ip.access.alignmentMin
        )
      }
    }
  }
}

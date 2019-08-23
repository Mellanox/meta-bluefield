SUMMARY = "Non-Volatile Memory Library"
DESCRIPTION = "The NVM Library is a library for using memory-mapped \
persistence, optimized specifically for persistent memory."
HOMEPAGE = "http://www.pmem.io"

inherit autotools pkgconfig

SRCREV = "0ca438ff39abcfa4955a9aaa615794baf2256344"
PV = "1.3+git${SRCPV}"
S = "${WORKDIR}/git"
B = "${S}"

LICENSE = "BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=afa254133a00eb16a585cdb106d26a21 \
                    file://src/jemalloc/COPYING;md5=301e99726ee358f32ffb45a2b450ecc4 \
                    file://src/windows/getopt/LICENSE.txt;md5=7c687aeb6df6e1a19cbbaf053b51a04d \
                    file://utils/check_license/check-license.c;md5=33d9896d6e534ca98bea114f4d37225c"

SRC_URI = "git://github.com/pmem/nvml.git"
SRC_URI_append = " file://aarch64.patch;striplevel=1"

EXTRA_OEMAKE = "CFLAGS='-Wno-unused-macros -Wno-implicit-function-declaration'"

EXTRA_OEMAKE_class-target = "prefix='${prefix}' sysconfdir='${sysconfdir}' \
  NORPATH=1 CROSS_COMPILE='${TARGET_PREFIX}' \
  ARCH=${TUNE_ARCH} \
  CONFIGURE_FLAGS='--host=${TARGET_SYS} --prefix=${prefix} \
  --exec-prefix=${exec_prefix} --sysconfdir=${sysconfdir}'"

do_install_append () {
        install -d ${D}/${datadir}/nvml
        install -c ${S}/utils/nvml.magic ${D}/${datadir}/nvml
}

INSANE_SKIP_${PN}-libpmempool += "dev-deps"
INSANE_SKIP_${PN}-libpmemobj += "dev-deps"
INSANE_SKIP_${PN}-libpmemblk += "dev-deps"
INSANE_SKIP_${PN}-libpmemlog += "dev-deps"

RPROVIDES_${PN} =+ "libpmem libpmemblk libpmemlog libpmemobj libpmempool \
                    libvmem libvmmalloc"

RPROVIDES_${PN} =+ "libpmem-dev libpmemblk-dev libpmemlog-dev libpmemobj-dev libpmempool-dev \
                    libvmem-dev libvmmalloc-dev libpmemobj++-dev"

RPROVIDES_${PN} =+ "libpmem-staticdev libpmemblk-staticdev libpmemlog-staticdev \
                    libpmemobj-staticdev libpmempool-staticdev libvmem-staticdev libvmmalloc-staticdev"

RPROVIDES_${PN} =+ "nvml-dbg"

PACKAGES = ""

PKG_${PN}-libpmem = "libpmem"
PACKAGES =+ "${PN}-libpmem"
FILES_${PN}-libpmem =+ "${libdir}/libpmem.so.*"
FILES_${PN}-libpmem =+ "${datadir}/nvml/nvml.magic"

PACKAGES =+ "${PN}-libpmem-dev"
FILES_${PN}-libpmem-dev =+ "${libdir}/libpmem.so"
FILES_${PN}-libpmem-dev =+ "${libdir}/pkgconfig/libpmem.pc"
FILES_${PN}-libpmem-dev =+ "${libdir}/nvml_debug/libpmem.so"
FILES_${PN}-libpmem-dev =+ "${libdir}/nvml_debug/libpmem.so.*"
FILES_${PN}-libpmem-dev =+ "${includedir}/libpmem.h"
FILES_${PN}-libpmem-dev =+ "${mandir}/man3/libpmem.3.gz"

PACKAGES =+ "${PN}-libpmem-staticdev"
FILES_${PN}-libpmem-staticdev =+ "${libdir}/libpmem.a"
FILES_${PN}-libpmem-staticdev =+ "${libdir}/nvml_debug/libpmem.a"

PKG_${PN}-libpmemblk = "libpmemblk"
PACKAGES =+ "${PN}-libpmemblk"
FILES_${PN}-libpmemblk =+ "${libdir}/libpmemblk.so.*"

PACKAGES =+ "${PN}-libpmemblk-dev"
FILES_${PN}-libpmemblk-dev =+ "${libdir}/libpmemblk.so"
FILES_${PN}-libpmemblk-dev =+ "${libdir}/pkgconfig/libpmemblk.pc"
FILES_${PN}-libpmemblk-dev =+ "${libdir}/nvml_debug/libpmemblk.so"
FILES_${PN}-libpmemblk-dev =+ "${libdir}/nvml_debug/libpmemblk.so.*"
FILES_${PN}-libpmemblk-dev =+ "${includedir}/libpmemblk.h"
FILES_${PN}-libpmemblk-dev =+ "${mandir}/man3/libpmemblk.3.gz"

PACKAGES =+ "${PN}-libpmemblk-staticdev"
FILES_${PN}-libpmemblk-staticdev =+ "${libdir}/libpmemblk.a"
FILES_${PN}-libpmemblk-staticdev =+ "${libdir}/nvml_debug/libpmemblk.a"

PKG_${PN}-libpmemlog = "libpmemlog"
PACKAGES =+ "${PN}-libpmemlog"
FILES_${PN}-libpmemlog =+ "${libdir}/libpmemlog.so.*"

PACKAGES =+ "${PN}-libpmemlog-dev"
FILES_${PN}-libpmemlog-dev =+ "${libdir}/libpmemlog.so"
FILES_${PN}-libpmemlog-dev =+ "${libdir}/pkgconfig/libpmemlog.pc"
FILES_${PN}-libpmemlog-dev =+ "${libdir}/nvml_debug/libpmemlog.so"
FILES_${PN}-libpmemlog-dev =+ "${libdir}/nvml_debug/libpmemlog.so.*"
FILES_${PN}-libpmemlog-dev =+ "${includedir}/libpmemlog.h"
FILES_${PN}-libpmemlog-dev =+ "${mandir}/man3/libpmemlog.3.gz"

PACKAGES =+ "${PN}-libpmemlog-staticdev"
FILES_${PN}-libpmemlog-staticdev =+ "${libdir}/libpmemlog.a"
FILES_${PN}-libpmemlog-staticdev =+ "${libdir}/nvml_debug/libpmemlog.a"

PKG_${PN}-libpmemobj = "libpmemobj"
PACKAGES =+ "${PN}-libpmemobj"
FILES_${PN}-libpmemobj =+ "${libdir}/libpmemobj.so.*"

PACKAGES =+ "${PN}-libpmemobj-dev"
FILES_${PN}-libpmemobj-dev =+ "${libdir}/libpmemobj.so"
FILES_${PN}-libpmemobj-dev =+ "${libdir}/pkgconfig/libpmemobj.pc"
FILES_${PN}-libpmemobj-dev =+ "${libdir}/nvml_debug/libpmemobj.so"
FILES_${PN}-libpmemobj-dev =+ "${libdir}/nvml_debug/libpmemobj.so.*"
FILES_${PN}-libpmemobj-dev =+ "${includedir}/libpmemobj.h"
FILES_${PN}-libpmemobj-dev =+ "${includedir}/libpmemobj/*.h"
FILES_${PN}-libpmemobj-dev =+ "${mandir}/man3/libpmemobj.3.gz"

PACKAGES =+ "${PN}-libpmemobj-staticdev"
FILES_${PN}-libpmemobj-staticdev =+ "${libdir}/libpmemobj.a"
FILES_${PN}-libpmemobj-staticdev =+ "${libdir}/nvml_debug/libpmemobj.a"

PKG_${PN}-libpmempool = "libpmempool"
PACKAGES =+ "${PN}-libpmempool"
FILES_${PN}-libpmempool =+ "${libdir}/libpmempool.so.*"
FILES_${PN}-libpmempool =+ "${bindir}/pmempool"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-info.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-create.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-dump.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-check.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-rm.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-convert.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-sync.1.gz"
FILES_${PN}-libpmempool =+ "${mandir}/man1/pmempool-transform.1.gz"
FILES_${PN}-libpmempool =+ "${sysconfdir}/bash_completion.d/pmempool.sh"

PACKAGES =+ "${PN}-libpmempool-dev"
FILES_${PN}-libpmempool-dev =+ "${libdir}/libpmempool.so"
FILES_${PN}-libpmempool-dev =+ "${libdir}/pkgconfig/libpmempool.pc"
FILES_${PN}-libpmempool-dev =+ "${libdir}/nvml_debug/libpmempool.so"
FILES_${PN}-libpmempool-dev =+ "${libdir}/nvml_debug/libpmempool.so.*"
FILES_${PN}-libpmempool-dev =+ "${includedir}/libpmempool.h"
FILES_${PN}-libpmempool-dev =+ "${mandir}/man3/libpmempool.3.gz"

PACKAGES =+ "${PN}-libpmempool-staticdev"
FILES_${PN}-libpmempool-staticdev =+ "${libdir}/libpmempool.a"
FILES_${PN}-libpmempool-staticdev =+ "${libdir}/nvml_debug/libpmempool.a"

PKG_${PN}-libvmem = "libvmem"
PACKAGES =+ "${PN}-libvmem"
FILES_${PN}-libvmem =+ "${libdir}/libvmem.so.*"

PACKAGES =+ "${PN}-libvmem-dev"
FILES_${PN}-libvmem-dev =+ "${libdir}/libvmem.so"
FILES_${PN}-libvmem-dev =+ "${libdir}/pkgconfig/libvmem.pc"
FILES_${PN}-libvmem-dev =+ "${libdir}/nvml_debug/libvmem.so"
FILES_${PN}-libvmem-dev =+ "${libdir}/nvml_debug/libvmem.so.*"
FILES_${PN}-libvmem-dev =+ "${includedir}/libvmem.h"
FILES_${PN}-libvmem-dev =+ "${mandir}/man3/libvmem.3.gz"

PACKAGES =+ "${PN}-libvmem-staticdev"
FILES_${PN}-libvmem-staticdev =+ "${libdir}/libvmem.a"
FILES_${PN}-libvmem-staticdev =+ "${libdir}/nvml_debug/libvmem.a"

PKG_${PN}-libvmmalloc = "libvmmalloc"
PACKAGES =+ "${PN}-libvmmalloc"
FILES_${PN}-libvmmalloc =+ "${libdir}/libvmmalloc.so.*"

PACKAGES =+ "${PN}-libvmmalloc-dev"
FILES_${PN}-libvmmalloc-dev =+ "${libdir}/libvmmalloc.so"
FILES_${PN}-libvmmalloc-dev =+ "${libdir}/pkgconfig/libvmmalloc.pc"
FILES_${PN}-libvmmalloc-dev =+ "${libdir}/nvml_debug/libvmmalloc.so"
FILES_${PN}-libvmmalloc-dev =+ "${libdir}/nvml_debug/libvmmalloc.so.*"
FILES_${PN}-libvmmalloc-dev =+ "${includedir}/libvmmalloc.h"
FILES_${PN}-libvmmalloc-dev =+ "${mandir}/man3/libvmmalloc.3.gz"

PACKAGES =+ "${PN}-libvmmalloc-staticdev"
FILES_${PN}-libvmmalloc-staticdev =+ "${libdir}/libvmmalloc.a"
FILES_${PN}-libvmmalloc-staticdev =+ "${libdir}/nvml_debug/libvmmalloc.a"

PACKAGES =+ "${PN}-libpmemobj++-dev"
FILES_${PN}-libpmemobj++-dev = "${libdir}/pkgconfig/libpmemobj++.pc"
FILES_${PN}-libpmemobj++-dev =+ "${includedir}/libpmemobj++/*.hpp"
FILES_${PN}-libpmemobj++-dev =+ "${includedir}/libpmemobj++/detail/*.hpp"
FILES_${PN}-libpmemobj++-dev =+ "${docdir}/libpmemobj++-dev/*"

PACKAGES =+ "${PN}-dbg"
FILES_${PN}-dbg = "/usr/src/debug"


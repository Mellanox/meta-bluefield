#
# ex:ts=4:sw=4:sts=4:et
# -*- tab-width: 4; c-basic-offset: 4; indent-tabs-mode: nil -*-
#

SUMMARY = "OpenFabrics Alliance InfiniBand Diagnostic Tools"
SECTION = "infiniband-diags"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/infiniband-diags-${PV}/COPYING;md5=0468527fc08cb212fcb6dec25462c271"

COMPATIBLE_HOST = "aarch64.*-linux"

inherit autotools pkgconfig systemd

SRC_URI = "https://github.com/linux-rdma/infiniband-diags/releases/download/2.1.0/infiniband-diags-${PV}.tar.gz"
SRC_URI[md5sum] = "134a1ddf31df7bc05ff81636f4e35779"
SRC_URI[sha256sum] = "63b546da8c529d669a792667d0c54e29346cb4f50bc747497b09ae3feaf0bf6c"

S = "${WORKDIR}/infiniband-diags-${PV}"

DEPENDS = "rdma-core opensm glib-2.0"

RDEPENDS_${PN} = "rdma-core opensm perl"

do_build[depends] += "glibc:do_populate_sysroot"

export LDFLAGS = " -L${STAGING_DIR_TARGET}${libdir} "
export CFLAGS = " -I${STAGING_DIR_TARGET}${includedir} -I${STAGING_DIR_TARGET}${includedir}/infiniband "
export AM_CPPFLAGS = "-I${STAGING_DIR_TARGET}${includedir} -I${STAGING_DIR_TARGET}${includedir}/infiniband \
						-I${S}/include -I${S}/libibnetdisc/include -I${S}/libibmad/include"

EXTRA_OEMAKE = "PREFIX=${prefix} CC='${CC}' DESTDIR=${D} LIBDIR=${libdir} CFGLAGS='${CFLAGS}' CPPFLAGS='${CFLAGS}' AM_CPPFLAGS='${AM_CPPFLAGS}'"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-dev"

FILES_${PN} += "usr/share/perl5"

do_install_append() {
	rm -rf ${D}/${localstatedir}/run
	rm -rf ${D}/${localstatedir}/var
}

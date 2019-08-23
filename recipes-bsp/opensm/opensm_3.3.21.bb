#
# ex:ts=4:sw=4:sts=4:et
# -*- tab-width: 4; c-basic-offset: 4; indent-tabs-mode: nil -*-
#

SUMMARY = "InfiniBand subnet manager and administration"
SECTION = "opensm"
LICENSE = "GPLv2|BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/opensm-${PV}/COPYING;md5=28deed445d322240b54a5f0d90f88a67"

COMPATIBLE_HOST = "aarch64.*-linux"

inherit autotools pkgconfig systemd

SRC_URI  = "https://github.com/linux-rdma/opensm/releases/download/${PV}/opensm-${PV}.tar.gz"
SRC_URI[md5sum] = "7149ad46987749ae80a00124dd1e3f9d"
SRC_URI[sha256sum] = "50d024090dc083274bc840792a3b539ecee5ad37a42948f43e84068e42b89b48"

S = "${WORKDIR}/opensm-${PV}"

DEPENDS = "rdma-core glib-2.0 bison-native"
RDEPENDS_${PN} = "rdma-core bash"

do_build[depends] += "glibc:do_populate_sysroot"

export CFLAGS += " -I${STAGING_DIR_TARGET}${includedir}"

EXTRA_OEMAKE = "PREFIX=${prefix} CC='${CC}' DESTDIR=${D} LIBDIR=${libdir} CFGLAGS='${CFLAGS}' CPPFLAGS='${CFLAGS}' \
				OSMV_INCLUDES='-I${STAGING_DIR_TARGET}${prefix}/include -I${S}/include' \
				OSMV_LDADD='-L${STAGING_DIR_TARGET}${libdir} -libumad'"

EXTRA_OECONF += "--libdir=${libdir} \
                --sysconfdir=${sysconfdir} \
                --prefix=${prefix} \
				--includedir=${includedir} \
				--with-osmv=openib \
				--with-umad-prefix=${STAGING_DIR_TARGET}${prefix}"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-dev"

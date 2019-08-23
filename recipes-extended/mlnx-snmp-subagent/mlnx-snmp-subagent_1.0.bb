require mlnx-snmp-subagent.inc

# Skip the unwanted steps
do_configure[noexec] = "1"

inherit systemd

SYSTEMD_SERVICE_${PN} = "mlnx-snmp-subagent.service"

SRC_URI = "file://mlnx_snmp_subagent_${PV}.tgz;subdir=${WORKDIR}/${BP} \
           file://MLNX-PMC-MIB.txt \
           file://mlnx-snmp-subagent.service"

export EXTRA_NET_SNMP_CONFIG="--cflags --sysroot=${STAGING_DIR_TARGET}"

FILES_${PN} =+ "${datadir}/snmp/mibs/MLNX-PMC-MIB.txt"
FILES_${PN} =+ "${systemd_system_unitdir}/mlnx-snmp-subagent.service"
FILES_${PN} =+ "${sbindir}/mlnx_snmp_subagent"

do_install () {
    [ -d "${B}" ] || exit 1
    [ -d "${S}" ] || exit 1
    cd ${B} || exit 1

    # Create dirs:
    #   /usr/share/snmp/mibs -- for Mellanox enterprise MIB file
    #   /lib/systemd/system  -- for Mellanox snmp subagent service file
    #   /usr/sbin            -- for Mellanox snmp subagent binary file
    install -d ${D}${datadir}/snmp/mibs
    install -d ${D}${systemd_system_unitdir}
    install -d ${D}${sbindir}

    # Install files into the image
    install -m 644 ${WORKDIR}/MLNX-PMC-MIB.txt ${D}${datadir}/snmp/mibs
    install -m 644 ${WORKDIR}/mlnx-snmp-subagent.service ${D}${systemd_system_unitdir}
    install -m 755 ${S}/mlnx_snmp_subagent ${D}${sbindir}
}

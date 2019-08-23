DESCRIPTION = "The mlnx-subagent package contains the BlueField Platform SNMP sub-agent."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=d694b57fe8981242ee927f5639ce060d"
DEPENDS = "net-snmp"
RDEPENDS_${PN} += "net-snmp-server"

inherit pkgconfig

FILES_${PN} += "${datadir}/snmp/*"

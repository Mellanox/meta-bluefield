DESCRIPTION = "Six is a Python 2 and 3 compatibility library."
HOMEPAGE = "https://pypi.org"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=35cec5bf04dd0820d0a18533ea7c774a"
DEPENDS = "python"

SRC_URI = "https://files.pythonhosted.org/packages/16/d8/bc6316cf98419719bd59c91742194c111b6f2e85abac88e496adefaf7afe/six-${PV}.tar.gz"
SRC_URI[md5sum] = "d12789f9baf7e9fb2524c0c64f1773f8"
SRC_URI[sha256sum] = "70e8a77beed4562e7f14fe23a786b54f6296e34344c23bc42f07b15018ff98e9"

S = "${WORKDIR}/six-${PV}"

inherit setuptools

RDEPENDS_${PN} += "python-lang python-shell python-io"
